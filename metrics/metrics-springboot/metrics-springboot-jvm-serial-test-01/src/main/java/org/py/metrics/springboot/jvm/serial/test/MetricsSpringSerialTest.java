package org.py.metrics.springboot.jvm.serial.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 * -XX:+UseSerialGC -XX:NativeMemoryTracking=summary
 */
@Component
@SpringBootApplication
public class MetricsSpringSerialTest {

    @Value("${spring.application.name}")
    private String application;

    public static void main(String[] args) {
        SpringApplication.run(MetricsSpringSerialTest.class, args);
    }
}
