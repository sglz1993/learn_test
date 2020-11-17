package org.py.d.apollo.springboot.test01;

import co.my.tetris.apollo.annotation.EnablemyApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author pengyue.du
 * @Date 2020/10/20 5:35 下午
 * @Description
 */
@EnablemyApolloConfig
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
