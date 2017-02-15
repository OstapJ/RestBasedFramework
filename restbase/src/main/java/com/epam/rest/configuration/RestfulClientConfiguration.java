package com.epam.rest.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.epam.rest.support.EnableRestfulClients;
import com.epam.rest.support.RestfulClientBeanPostProcessor;
import com.epam.rest.support.RestfulClientHandler;

/**
 * Spring configuration
 */
@Configuration
@EnableRestfulClients(basePackage = "com.epam.rest")
@ComponentScan(basePackages = { "com.epam.rest" })
public class RestfulClientConfiguration {

    @Autowired
    private RestfulClientContext context;

    @Bean
    public RestfulClientHandler restfulClientHandler() {
        return new RestfulClientHandler();
    }

    @Bean
    public RestfulClientBeanPostProcessor restfulClientBeanPostProcessor() {
        return new RestfulClientBeanPostProcessor();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(apacheHttpClient()));
    }

    public HttpClient apacheHttpClient() {
        return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager())
                .addInterceptorFirst(context().preemtiveRequestInterceptor())
                .setDefaultHeaders(context.oAuthCredentialsProvider()).build();
    }

    public HttpClientConnectionManager poolingHttpClientConnectionManager() {
        return new PoolingHttpClientConnectionManager();
    }

    public RestfulClientContext context() {
        return context;
    }
}
