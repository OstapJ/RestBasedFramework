package com.epam.rest.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javassist.util.proxy.ProxyObject;

/**
 * Post process to assign handler to REST client interface proxy
 */
@Component
public class RestfulClientBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private RestfulClientHandler handler;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.startsWith(SupportConstants.PROXIED_INTERFACES_BEAN_NAME_PREF)){
            ProxyObject.class.cast(bean).setHandler(handler);
        }
        return bean;
    }
}
