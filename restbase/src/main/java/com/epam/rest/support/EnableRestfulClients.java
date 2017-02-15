package com.epam.rest.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Marker to perform registration of REST client proxies
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RestfulClientRegistrar.class)
public @interface EnableRestfulClients {

    String basePackage();
}
