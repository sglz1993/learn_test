package org.py.test.mybatis.config;

import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author pengyue.du
 * @Date 2020/10/15 3:26 下午
 * @Description
 */
@Configuration
public class Prometheus_Config {

//    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        DefaultExports.initialize();
        return new ServletRegistrationBean(new MetricsServlet(), "/metrics");
    }


//    @Bean
//    @ConditionalOnMissingBean
//    public CollectorRegistry collectorRegistry() {
//        return CollectorRegistry.defaultRegistry;
//    }
}
