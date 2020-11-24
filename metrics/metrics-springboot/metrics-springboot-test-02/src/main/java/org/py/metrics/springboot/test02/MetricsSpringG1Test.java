package org.py.metrics.springboot.test02;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.hotspot.*;
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
        register(CollectorRegistry.defaultRegistry);
        SpringApplication.run(MetricsSpringG1Test.class, args);
    }

    public static void register(CollectorRegistry registry) {
//        new StandardExports().register(registry);
        new MemoryPoolsExports().register(registry);
        new MemoryAllocationExports().register(registry);
        new BufferPoolsExports().register(registry);
        new GarbageCollectorExports().register(registry);
        new ThreadExports().register(registry);
        new ClassLoadingExports().register(registry);
        new VersionInfoExports().register(registry);
    }
}
