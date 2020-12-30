package org.py.d.apollo.springboot.test01;

import org.py.apollo.annotation.EnablePyApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author pengyue.du
 * @Date 2020/10/20 5:35 下午
 * @Description
 */
@EnablePyApolloConfig
@SpringBootApplication
public class SpringMyApolloApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMyApolloApplication.class, args);
    }
}
