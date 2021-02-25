package org.py.spring.mybatis.hello;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pengyue.du
 */
@MapperScan(basePackages = {"org.py.**.mapper.api"})
//@MapperScan(basePackages = {"org.py.spring.mybatis.hello"})
@SpringBootApplication
public class MybatisHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisHelloApplication.class, args);
    }

}
