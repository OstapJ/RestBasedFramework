package com.epam.rest.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpMethod;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.epam.rest.model.annotation.HttpMethodType;
import com.epam.rest.model.annotation.HttpResponse;
import com.epam.rest.model.annotation.ResourcePath;

/**
 * Maps method specifications to model object
 */
public class RestfulCallModelMapper {

    private List<Method> restfulCalls;

    private RestfulCallModelMapper(Class<?> clientInterface){
        restfulCalls = getRestfullCallMethods(clientInterface);
    }

    public static boolean isRestfulCall(Method method){
        Annotation[] annotations = method.getDeclaredAnnotations();
        if(null != annotations){
            for(Annotation annotation : annotations){
                if(annotation.annotationType().isAnnotationPresent(HttpMethodType.class)){
                    return true;
                }
            }
        }
        return false;
    }

    private List<Method> getRestfullCallMethods(Class<?> clientInterface){
        List<Method> restfulCalls = Lists.newArrayList();
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(clientInterface);
        if(null != methods){
            for(Method method : methods){
                if(isRestfulCall(method)){
                    restfulCalls.add(method);
                }
            }
        }
        return restfulCalls;
    }

    public static RestfulCallModelMapper sourceInterface(Class<?> clientInterface){
        return new RestfulCallModelMapper(clientInterface);
    }

    public Map<Method, RestfulCallModel> map(){
        Map<Method, RestfulCallModel> modelMapping = Maps.newHashMap();
        for(Method restfulCall : restfulCalls){
            RestfulCallModel restfulCallModel = new RestfulCallModel();
            restfulCallModel.setHttpMethod(httpMethod(restfulCall));
            restfulCallModel.setResourcePath(resourcePath(restfulCall));
            restfulCallModel.setResponseType(reponseClass(restfulCall));
            modelMapping.put(restfulCall, restfulCallModel);
        }
        return modelMapping;
    }

    public HttpMethod httpMethod(Method method){
        Annotation[] annotations = method.getDeclaredAnnotations();
        if(null != annotations){
            for(Annotation annotation : annotations){
                Class<?> annotationType = annotation.annotationType();
                if(annotationType.isAnnotationPresent(HttpMethodType.class)){
                    return annotationType.getAnnotation(HttpMethodType.class).value();
                }
            }
        }
        return method.getAnnotation(HttpMethodType.class).value();
    }

    public String resourcePath(Method method){
        if(method.isAnnotationPresent(ResourcePath.class)){
            ResourcePath resource = method.getAnnotation(ResourcePath.class);
            return resource.value();
        }
        return "/";
    }

    public Class<?> reponseClass(Method method){
        if(method.isAnnotationPresent(HttpResponse.class)){
            return GenericTypeResolver.resolveReturnTypeArgument(method, HttpResponseModel.class);
        }else{
            return method.getReturnType();
        }
    }
}
