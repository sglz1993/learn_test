package org.py.test.mybatis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 * -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:-UseGCOverheadLimit -XX:+CMSScavengeBeforeRemark -XX:+UseCMSInitiatingOccupancyOnly -XX:NativeMemoryTracking=summary
 */
@Component
@SpringBootApplication
public class MetricsSpringCmsTest {

    @Value("${spring.application.name}")
    private String application;

    public static void main(String[] args) {
        SpringApplication.run(MetricsSpringCmsTest.class, args);
    }
}
