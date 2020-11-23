package org.py.metrics.springboot.test02;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 * -XX:+UseG1GC -XX:NativeMemoryTracking=summary
 */
@Component
@SpringBootApplication
public class MetricsSpringG1Test {

    @Value("${spring.application.name}")
    private String application;

    public static void main(String[] args) {
        SpringApplication.run(MetricsSpringG1Test.class, args);
    }
}
