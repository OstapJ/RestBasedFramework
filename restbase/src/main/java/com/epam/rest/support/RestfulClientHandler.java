package com.epam.rest.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.epam.rest.ApplicationContextProvider;
import com.google.common.collect.Maps;

import javassist.util.proxy.MethodHandler;
import com.epam.rest.configuration.RestfulClientContext;
import com.epam.rest.model.RestfulCallModel;
import com.epam.rest.model.RestfulCallModelMapper;
import com.epam.rest.model.UriPathTemplate;
import com.epam.rest.model.annotation.Body;
import com.epam.rest.model.annotation.Header;
import com.epam.rest.model.annotation.HttpResponse;
import com.epam.rest.model.annotation.Path;
import com.epam.rest.model.annotation.Query;

/**
 * Handles REST client proxy methods invocations.
 */
@Component
public class RestfulClientHandler implements MethodHandler {

    @Autowired
    private RestfulClientContext context;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RestfulClientHandler.class);

    /**
     * Performs look up of interface model, builds uri for requests, delegates execution to rest tempaltes
     * and handles the response.
     *
     * @param self
     * @param thisMethod
     * @param proceed
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        if (RestfulCallModelMapper.isRestfulCall(thisMethod)) {
            restTemplate
                    .setMessageConverters(Arrays.asList((MappingJackson2HttpMessageConverter) ApplicationContextProvider
                            .getApplicationContext().getBean("mappingJackson2HttpMessageConverter")));
            RestfulCallModel restfulCallModel = RestfulClientInventory.lookup(self).get(thisMethod);
            String url = uri(context().getEndpoint(), restfulCallModel.getResourcePath(), thisMethod, args);
            HttpEntity entity = entity(thisMethod, args);
            ResponseEntity response = null;
            LOGGER.debug("{} {} resource ", restfulCallModel.getHttpMethod(), url);
            try {
                response = restTemplate().exchange(url, restfulCallModel.getHttpMethod(), entity,
                        restfulCallModel.getResponseType());
                return resolveResponse(thisMethod, response, null);
            } catch (HttpClientErrorException e) {
                return resolveResponse(thisMethod, response, e);
            }
        } else {
            return proceed.invoke(self, args);
        }
    }

    public RestfulClientContext context() {
        return context;
    }

    public RestTemplate restTemplate() {
        return restTemplate;
    }

    /**
     * Build URI for HTTP request from client interface specification and context
     *
     * @param endpoint
     * @param path
     * @param method
     * @param args
     * @return
     */
    private String uri(String endpoint, String path, Method method, Object[] args) {
        Map<String, Object> pathParams = getParametersMapping(Path.class, method, args);
        Map<String, Object> queryParams = getParametersMapping(Query.class, method, args);
        return uriTemplate(endpoint, path).merge().expand(pathParams).appendQueryParameters(queryParams).build();
    }

    private UriPathTemplate uriTemplate(String endpoint, String resourcePath) {
        return UriPathTemplate.create(new StringBuilder().append(endpoint).append(resourcePath).toString());
    }

    /**
     * Builds HTTP request entity from client interface specification
     *
     * @param method
     * @param args
     * @return
     */
    private HttpEntity entity(Method method, Object[] args) {
        Map<String, Object> bodyMapping = getParametersMapping(Body.class, method, args);
        HttpEntity entity = !bodyMapping.isEmpty() ? new HttpEntity(bodyMapping.get(Body.class.getName()))
                : HttpEntity.EMPTY;
        Map<String, Object> headerMapping = getParametersMapping(Header.class, method, args);
        Iterator<Map.Entry<String, Object>> iterator = headerMapping.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> headerEntry = iterator.next();
            Object headerValue = headerEntry.getValue();
            if (null != headerValue) {
                entity.getHeaders().add(headerEntry.getKey(), headerValue.toString());
            }
        }
        return entity;
    }

    private Map<String, Object> getParametersMapping(Class<? extends Annotation> annotationToMap, Method method,
            Object[] args) {
        try {
            Map<String, Object> mappings = Maps.newHashMap();
            Annotation[][] parametersAnnotations = method.getParameterAnnotations();
            if (null != parametersAnnotations) {
                for (int paramIndex = 0; paramIndex < parametersAnnotations.length; paramIndex++) {
                    Annotation[] paramAnnotations = parametersAnnotations[paramIndex];
                    int annotationIndex = findParamAnnotaion(paramAnnotations, annotationToMap);
                    if (annotationIndex >= 0) {
                        Annotation paramAnnotation = paramAnnotations[annotationIndex];
                        String paramName = null;
                        try {
                            Method paramNameExtractMethod = paramAnnotation.annotationType()
                                    .getMethod(SupportConstants.PARAM_ANNOTATIONS_METHOD);
                            paramName = String.class.cast(paramNameExtractMethod.invoke(paramAnnotation));
                        } catch (NoSuchMethodException e) {
                            paramName = paramAnnotation.annotationType().getName();
                        }
                        if (null != paramName) {
                            mappings.put(paramName, args[paramIndex]);
                        }
                    }
                }
            }
            return mappings;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private int findParamAnnotaion(Annotation[] paramAnnotations, Class<? extends Annotation> annotationToSearch) {
        for (int annotaitonIndex = 0; annotaitonIndex < paramAnnotations.length; annotaitonIndex++) {
            if (annotationToSearch.equals(paramAnnotations[annotaitonIndex].annotationType())) {
                return annotaitonIndex;
            }
        }
        return -1;
    }

    private Object resolveResponse(Method method, ResponseEntity entity, HttpClientErrorException exception) {
        try {
            if (method.isAnnotationPresent(HttpResponse.class)) {
                Class<?> httpReponseType = method.getReturnType();

                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                if(exception == null){
                    LOGGER.debug("Response {}\n{}",entity.getStatusCode(), gson.toJson(entity.getBody()));
                    return httpReponseType.getConstructor(ResponseEntity.class).newInstance(entity);
                }
                else {
                    return httpReponseType.getConstructor(HttpClientErrorException.class).newInstance(exception);
                }
            } else {
                return entity.getBody();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Unable to resolve response", e);
        }
    }
}
