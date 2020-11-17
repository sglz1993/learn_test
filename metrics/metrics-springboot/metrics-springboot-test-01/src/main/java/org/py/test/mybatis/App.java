package org.py.test.mybatis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 */
@Component
@SpringBootApplication
public class App {

    @Value("${spring.application.name}")
    private String application;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
//    @Bean
//    MeterRegistryCustomizer<MeterRegistry> configurer() {
//        return (registry) -> registry.config().commonTags("application", application);
//    }
}
