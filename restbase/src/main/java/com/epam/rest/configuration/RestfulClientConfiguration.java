package com.epam.rest.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.epam.rest.support.EnableRestfulClients;

/**
 * Spring configuration
 */
@Configuration
@EnableRestfulClients(basePackage = "com.epam.rest")
public class RestfulClientConfiguration {

    @Autowired
    private RestfulClientContext context;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(apacheHttpClient()));
    }

    public HttpClient apacheHttpClient() {
        return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager())
                // .addInterceptorFirst(context().preemtiveRequestInterceptor())
                .setDefaultHeaders(context.oAuthCredentialsProvider()).build();
    }

    public HttpClientConnectionManager poolingHttpClientConnectionManager() {
        // return new PoolingHttpClientConnectionManager();
        return new BasicHttpClientConnectionManager();
    }

    public RestfulClientContext context() {
        return context;
    }
}
