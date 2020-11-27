package org.py.p6spy.user.v1.config;

import org.py.p6spy.client.plugs.SQLAnalyseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6spyConfig {

    @Bean
    public SQLAnalyseConfig sqlAnalyseConfig() {
        return new SQLAnalyseConfig("123", "user-test", 1, "SQLRecordTopic", "127.0.0.1:9092");
    }

}
