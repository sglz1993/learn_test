package org.py.spring.mybatisplus.hello;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pengyue.du
 */
@MapperScan("org.py.spring.mybatisplus.hello.mapper.api")
@SpringBootApplication
public class MyBatisPlusHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusHelloApplication.class, args);
    }

}
