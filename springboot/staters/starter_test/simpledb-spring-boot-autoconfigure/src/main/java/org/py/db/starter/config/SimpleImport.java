package org.py.db.starter.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengyue.du
 */
public class SimpleImport implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {


        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
        builder.addPropertyValue("processPropertyPlaceHolders", true);


        builder.addPropertyValue("nameGenerator", BeanUtils.instantiateClass(MyBeanNameGenerator.class));

        List<String> basePackages = new ArrayList<>();

        builder.addPropertyValue("basePackage", "org.py.db.starter.mapper.api");

        registry.registerBeanDefinition("_mysimpleStarterImportBeanName", builder.getBeanDefinition());

    }
}
