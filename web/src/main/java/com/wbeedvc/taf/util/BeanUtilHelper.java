package com.wbeedvc.taf.util;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class BeanUtilHelper {

    public static void copyProperties(final Object destination, final Object source, final String exceptionMessage) {
        try {
            BeanUtils.copyProperties(destination, source);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(exceptionMessage, e);
        }
    }
}
