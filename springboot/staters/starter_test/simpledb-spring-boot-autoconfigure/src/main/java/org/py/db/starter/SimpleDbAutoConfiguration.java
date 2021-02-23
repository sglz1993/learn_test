package org.py.db.starter;

import org.py.db.starter.config.SimpleImport;
import org.py.db.starter.controller.TestController;
import org.py.db.starter.mapper.api.TestMapper;
import org.py.db.starter.service.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author pengyue.du
 */
@Import(SimpleImport.class)
@org.springframework.context.annotation.Configuration
public class SimpleDbAutoConfiguration {

    @Bean("simpleTestController")
    public TestController testController(TestMapper testMapper, TestService testService) {
        return new TestController(testMapper, testService);
    }

    @Bean("simpleTestService")
    public TestService testService(TestMapper testMapper) {
        return new TestService(testMapper);
    }

}