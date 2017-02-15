package com.epam.rest;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Created by U0160473 on 1/29/2015.
 */
@Component("propertiesManager")
public class DefaultPropertiesManager implements PropertiesManager {
    @Autowired
    private ResourceBundleMessageSource messageSource;

    @Override
    public String get(String key) {
        // allow for properties to be passed in from the command line
        String value = System.getProperty(key);

        if (value == null) {
            Object o = messageSource.getMessage(key, null, Locale.getDefault());
            value = o != null ? o.toString() : null;
        }

        return value;
    }

    @Override
    public String get(String key, String... values) {
        Object o = messageSource.getMessage(key, values, Locale.getDefault());

        return o != null ? o.toString() : null;
    }
}
