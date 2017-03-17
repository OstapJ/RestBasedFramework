package com.epam.rest.support;

import java.lang.reflect.Method;
import java.util.Map;

import com.epam.rest.model.RestfulCallModel;
import com.epam.rest.model.RestfulCallModelMapper;
import com.epam.rest.model.annotation.RestfulClient;
import com.google.common.collect.Maps;

/**
 * Holds mappings of proxy class to rest client interface model
 */
class RestfulClientInventory {

    private static final Map<Class<?>, Map<Method, RestfulCallModel>> SPECS = Maps.newHashMap();

    private RestfulClientInventory() {
    }

    /**
     * Save mapping of client interface to method model
     *
     * @param clientInterface
     * @return
     */
    public static void addSpec(Class<?> clientInterface) {
        SPECS.put(clientInterface, RestfulCallModelMapper.sourceInterface(clientInterface).map());
    }

    /**
     * Get method model for client interface
     *
     * @param clientInterface
     * @return
     */
    public static Map<Method, RestfulCallModel> lookup(Class<?> clientInterface) {
        Map<Method, RestfulCallModel> spec = SPECS.get(clientInterface);
        if (null == spec) {
            throw new IllegalStateException(new StringBuilder().append("\"Specification for restful client interface {")
                    .append(clientInterface).append("} has not been found").toString());
        }
        return spec;
    }

    public static Map<Method, RestfulCallModel> lookup(Object proxy) {
        Class<?> clientInterface = getClientInterfaceForProxy(proxy);
        return lookup(clientInterface);
    }

    public static Class<?> getClientInterfaceForProxy(Object object) {
        Class<?>[] interfaces = object.getClass().getInterfaces();
        if (null == interfaces) {
            throw new IllegalStateException(new StringBuilder().append("Proxy {").append(object.getClass())
                    .append("} does not implement any interface").toString());
        }
        for (Class<?> clientInterface : interfaces) {
            if (clientInterface.isAnnotationPresent(RestfulClient.class)) {
                return clientInterface;
            }
        }
        throw new IllegalStateException(new StringBuilder().append("Proxy {").append(object.getClass())
                .append("} does not implement interface, marked with {").append(RestfulClient.class).append("}")
                .toString());
    }
}
