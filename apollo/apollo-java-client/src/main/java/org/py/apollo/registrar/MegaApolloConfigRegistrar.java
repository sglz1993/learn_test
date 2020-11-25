package org.py.apollo.registrar;

import co.mega.tetris.apollo.annotation.EnableMegaApolloConfig;
import co.mega.tetris.apollo.config.SpringConfig;
import co.mega.tetris.apollo.config.SpringConfigMonitor;
import co.mega.tetris.apollo.log.LoggerConfiguration;
import com.ctrip.framework.apollo.spring.annotation.ApolloAnnotationProcessor;
import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValueProcessor;
import com.ctrip.framework.apollo.spring.annotation.SpringValueProcessor;
import com.ctrip.framework.apollo.spring.config.PropertySourcesProcessor;
import com.ctrip.framework.apollo.spring.property.SpringValueDefinitionProcessor;
import com.ctrip.framework.apollo.spring.util.BeanRegistrationUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: pengyue.du
 * @time: 2020/7/14 4:01 pm
 */
public class MegaApolloConfigRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableMegaApolloConfig.class.getName()));
        String[] namespaces = attributes.getStringArray("value");
        int order = attributes.getNumber("order");
        PropertySourcesProcessor.addNamespaces(Lists.newArrayList(namespaces), order);

        Map<String, Object> propertySourcesPlaceholderPropertyValues = new HashMap<>();
        // to make sure the default PropertySourcesPlaceholderConfigurer's priority is higher than PropertyPlaceholderConfigurer
        propertySourcesPlaceholderPropertyValues.put("order", 0);

        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, PropertySourcesPlaceholderConfigurer.class.getName(),
                PropertySourcesPlaceholderConfigurer.class, propertySourcesPlaceholderPropertyValues);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, PropertySourcesProcessor.class.getName(),
                PropertySourcesProcessor.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, ApolloAnnotationProcessor.class.getName(),
                ApolloAnnotationProcessor.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, SpringValueProcessor.class.getName(),
                SpringValueProcessor.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, SpringValueDefinitionProcessor.class.getName(),
                SpringValueDefinitionProcessor.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, ApolloJsonValueProcessor.class.getName(),
                ApolloJsonValueProcessor.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, SpringConfig.class.getName(), SpringConfig.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, SpringConfigMonitor.class.getName(), SpringConfigMonitor.class);
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, LoggerConfiguration.class.getName(), LoggerConfiguration.class);

    }

}