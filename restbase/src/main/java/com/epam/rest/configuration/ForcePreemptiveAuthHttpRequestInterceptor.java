package com.epam.rest.configuration;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthState;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;

/**
 * Add ability to perform preemptive requests with Apache Http client
 */
public class ForcePreemptiveAuthHttpRequestInterceptor implements HttpRequestInterceptor {

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        AuthState authState = (AuthState) context.getAttribute(HttpClientContext.TARGET_AUTH_STATE);
        if (authState.getAuthScheme() == null) {
            HttpHost targetHost = (HttpHost) context.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
            AuthCache authCache = new BasicAuthCache();
            authCache.put(targetHost, new BasicScheme());
            context.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
        }
    }
}
