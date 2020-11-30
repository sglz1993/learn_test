package org.py.p6spy.user.v1.config;

import com.google.common.collect.Lists;
import org.py.p6spy.client.plugs.SQLAnalyseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6spyConfig {

//    @Value("${app.id}")
//    private String appId;
//
//    @Value("${service.name}")
//    private String serviceName;
//
//    @Value("${bootstrap.servers:1}")
//    private int samplingFrequency;
//
//    @Value("${bootstrap.servers}")
//    private String bootstrapServers;
//
//    @Value("${partitions:1}")
//    private int partitions;
//
//    @Value("${replics:1}")
//    private int replics;
    @Bean
    public SQLAnalyseConfig sqlAnalyseConfig() {
        SQLAnalyseConfig.setPartitions(1);
        SQLAnalyseConfig.setReplics(1);
        return new SQLAnalyseConfig("123", "user-test", 1, "127.0.0.1:9092", Lists.newArrayList());
    }

}
