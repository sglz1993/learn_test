package org.py.spring.mybatis.hello;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

/**
 * @author pengyue.du
 */
@MapperScan(basePackages = {"org.py.**.mapper.api"})
public class MybatisHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisHelloApplication.class, args);
    }

}
