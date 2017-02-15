package com.epam.rest.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.HttpMethod;

/**
 * Specifies that HTTP method is POST for method,
 * marked with this annotation
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@HttpMethodType(HttpMethod.POST)
public @interface Post {
}
