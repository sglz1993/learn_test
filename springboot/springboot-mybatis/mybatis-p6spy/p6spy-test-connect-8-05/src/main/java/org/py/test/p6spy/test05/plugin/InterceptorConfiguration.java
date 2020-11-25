package org.py.test.p6spy.test05.plugin;

import org.py.test.p6spy.test05.plugin.interceptor.SqlLoggerInterceptor;
import org.py.test.p6spy.test05.plugin.interceptor.myMybatisInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author pengyue.du
 * @Date 2020/9/9 3:37 下午
 * @Description
 */
@Component
public class InterceptorConfiguration {

    @Bean
    public SqlLoggerInterceptor sqlCostInterceptor() {
        return new SqlLoggerInterceptor();
    }

    @Bean
    public myMybatisInterceptor myMybatisInterceptor() {
        return new myMybatisInterceptor();
    }

}
