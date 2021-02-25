package org.py.db.starter;

import org.py.db.starter.service.TestService;
import org.springframework.context.annotation.Bean;

/**
 * @author pengyue.du
 */
//@org.springframework.context.annotation.Configuration
public class SimpleDbAutoConfiguration {


    @Bean("simpleTestService")
    public TestService testService() {
        return new TestService();
    }

}
