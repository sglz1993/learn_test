package org.py.metrics.springboot.jvm.parallel.test;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.hotspot.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 * -XX:+UseParallelGC -XX:NativeMemoryTracking=summary
 */
@Component
@SpringBootApplication
public class MetricsSpringParallelTest {

    @Value("${spring.application.name}")
    private String application;

    public static void main(String[] args) {
        register(CollectorRegistry.defaultRegistry);
        SpringApplication.run(MetricsSpringParallelTest.class, args);
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
