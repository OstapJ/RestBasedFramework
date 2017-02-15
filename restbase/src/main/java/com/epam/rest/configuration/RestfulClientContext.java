package com.epam.rest.configuration;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

/**
 * Context parameters
 */
@Component
public class RestfulClientContext {

    @Value("${api.user.token}")
    private String token;

    @Value("${api.endpoint}")
    private String endpoint;

    public List<Header> oAuthCredentialsProvider() {
        BasicHeader header = new BasicHeader(HttpHeaders.AUTHORIZATION, token);
        return Lists.newArrayList(header);
    }

    public ForcePreemptiveAuthHttpRequestInterceptor preemtiveRequestInterceptor() {
        return new ForcePreemptiveAuthHttpRequestInterceptor();
    }

    public String getToken() {
        return token;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
