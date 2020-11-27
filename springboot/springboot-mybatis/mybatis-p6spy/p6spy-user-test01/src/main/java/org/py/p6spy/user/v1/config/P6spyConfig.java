package org.py.p6spy.user.v1.config;

import org.py.p6spy.client.plugs.SQLAnalyseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6spyConfig {

    @Bean
    public SQLAnalyseConfig sqlAnalyseConfig() {
        return new SQLAnalyseConfig("user-test", 1);
    }

}
