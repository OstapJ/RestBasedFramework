package com.epam.rest.support;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.epam.rest.model.annotation.RestfulClient;

/**
 * Scanning the REST client interfaces in classpath
 */
public class RestfulClientComponentProvider extends ClassPathScanningCandidateComponentProvider {

    public RestfulClientComponentProvider() {
        super(false);
        addIncludeFilter(new AnnotationTypeFilter(RestfulClient.class, true, true));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }
}
