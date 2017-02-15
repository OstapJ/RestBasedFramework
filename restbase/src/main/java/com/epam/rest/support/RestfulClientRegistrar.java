package com.epam.rest.support;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import javassist.util.proxy.ProxyFactory;

/**
 * Registers REST client interfaces beans
 */
public class RestfulClientRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;
    private ClassPathScanningCandidateComponentProvider provider;

    public RestfulClientRegistrar(){
        provider = new RestfulClientComponentProvider();
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String basePackage = getComponentScanPackage(importingClassMetadata, EnableRestfulClients.class);
        Set<BeanDefinition> candidates = provider.findCandidateComponents(basePackage);
        registerProxyBean(candidates, registry);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Get root package to scan for candidates
     * @param importingClassMetadata
     * @param metainfoAnnotation
     * @return
     */
    private String getComponentScanPackage(AnnotationMetadata importingClassMetadata, Class<? extends Annotation> metainfoAnnotation){
        Assert.isTrue(importingClassMetadata.hasAnnotation(metainfoAnnotation.getName()));
        Map<String,Object> attributes = importingClassMetadata.getAnnotationAttributes(metainfoAnnotation.getName());
        Assert.notNull(attributes);
        Assert.isTrue(attributes.containsKey(SupportConstants.ENABLE_RESTFUL_CLIENTS_BASE_PACKAGE_ATTR));
        Object basePackage = attributes.get(SupportConstants.ENABLE_RESTFUL_CLIENTS_BASE_PACKAGE_ATTR);
        Assert.notNull(basePackage);
        return basePackage.toString();
    }

    /**
     * Loads REST client interface class
     *
     * @param name
     * @return
     */
    private Class<?> loadInterface(String name){
        try {
            return resourceLoader.getClassLoader().loadClass(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(new StringBuilder().append("Restful client interface {")
                    .append(name).append("}").append(" cannot be loaded").toString(),e);
        }
    }

    /**
     * Create bean definition for REST client interface and registers it
     * @param candidates
     * @param registry
     */
    private void registerProxyBean(Set<BeanDefinition> candidates, BeanDefinitionRegistry registry){
        for(BeanDefinition candidate : candidates){
            Class<?> definitionProxyClass = createProxyClassForInterface(candidate);
            BeanDefinition proxyBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(definitionProxyClass).getBeanDefinition();
            proxyBeanDefinition.setAutowireCandidate(true);
            registry.registerBeanDefinition(new StringBuilder()
                            .append(SupportConstants.PROXIED_INTERFACES_BEAN_NAME_PREF)
                            .append(candidate.getBeanClassName()).toString(),
                    proxyBeanDefinition);
        }
    }

    /**
     * Creates proxy class for REST client interface
     * @param definition
     * @return
     */
    private Class<?> createProxyClassForInterface(BeanDefinition definition){
        Class<?> restfulClientInterface = loadInterface(definition.getBeanClassName());
        RestfulClientInventory.addSpec(restfulClientInterface);
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(new Class[]{restfulClientInterface});
        return proxyFactory.createClass();
    }


}
