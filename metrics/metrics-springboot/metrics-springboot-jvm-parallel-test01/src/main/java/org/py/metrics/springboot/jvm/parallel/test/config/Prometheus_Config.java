package org.py.metrics.springboot.jvm.parallel.test.config;

import io.prometheus.client.CollectorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author pengyue.du
 * @Date 2020/10/15 3:26 下午
 * @Description
 */
@Configuration
public class Prometheus_Config {


    @Bean
    public CollectorRegistry collectorRegistry() {
        return CollectorRegistry.defaultRegistry;
    }
}
