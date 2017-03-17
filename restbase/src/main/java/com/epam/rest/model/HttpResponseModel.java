package com.epam.rest.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Wrapper for HTTP response
 * @param <T>
 */
public class HttpResponseModel<T> {

    private HttpStatus statusCode;
    private HttpHeaders headers;
    private T dto;

    // TODO: replace with POJO mapped to JSON
    private String errorMessage;

    public HttpResponseModel(ResponseEntity<T> entity) {
        statusCode = entity.getStatusCode();
        headers = entity.getHeaders();
        dto = entity.getBody();
    }

    public HttpResponseModel(HttpClientErrorException exception) {
        statusCode = exception.getStatusCode();
        headers = exception.getResponseHeaders();
        errorMessage = exception.getResponseBodyAsString();
        dto = null;
    }

    public T dto() {
        return dto;
    }

    public HttpStatus statusCode() {
        return statusCode;
    }

    public boolean hasErrors() {
        return StringUtils.isNotBlank(errorMessage);
    }

    public String contentType() {
        return header(HttpHeaders.CONTENT_TYPE);
    }

    public HttpHeaders allHeaders() {
        return headers;
    }

    public List<String> headerValues(String name) {
        return allHeaders().get(name);
    }

    public String header(String name) {
        List<String> headers = headerValues(name);
        return (headers != null && !headers.isEmpty()) ? headers.get(0) : null;
    }

    public String errorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HttpResponseModel)) {
            return false;
        }

        HttpResponseModel that = (HttpResponseModel) o;

        if (dto != null ? !dto.equals(that.dto) : that.dto != null)
            return false;
        if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null)
            return false;
        if (headers != null ? !headers.equals(that.headers) : that.headers != null)
            return false;
        if (statusCode != that.statusCode)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusCode != null ? statusCode.hashCode() : 0;
        result = 31 * result + (headers != null ? headers.hashCode() : 0);
        result = 31 * result + (dto != null ? dto.hashCode() : 0);
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HttpResponseModel{" + "statusCode=" + statusCode + ", headers=" + headers + ", dto=" + dto
                + ", errorMessage='" + errorMessage + '\'' + '}';
    }
}
