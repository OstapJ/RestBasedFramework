package com.epam.rest.model;

import org.springframework.http.HttpMethod;

/**
 * Describes meta-information of REST service interface
 */
public class RestfulCallModel {

    private HttpMethod httpMethod;
    private String resourcePath;
    private Class<?> responseType;

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Class<?> getResponseType() {
        return responseType;
    }

    public void setResponseType(Class<?> responseType) {
        this.responseType = responseType;
    }
}
