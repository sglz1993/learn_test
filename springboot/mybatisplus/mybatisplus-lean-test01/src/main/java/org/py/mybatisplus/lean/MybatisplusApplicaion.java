package org.py.mybatisplus.lean;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.py.mybatisplus.lean.generator.lean.mapper")
@SpringBootApplication
public class MybatisplusApplicaion {

    public static void main(String[] args) {
        SpringApplication.run(MybatisplusApplicaion.class, args);
    }
}
