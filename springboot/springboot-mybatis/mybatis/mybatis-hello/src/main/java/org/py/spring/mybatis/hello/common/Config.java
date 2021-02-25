package org.py.spring.mybatis.hello.common;

import com.alibaba.fastjson.JSON;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> System.out.println(JSON.toJSONString(configuration, true));
    }

//    @Bean
//    public void obj(MapperScannerConfigurer mapperScannerConfigurer) {
//        mapperScannerConfigurer.getNameGenerator();
//    }

}
