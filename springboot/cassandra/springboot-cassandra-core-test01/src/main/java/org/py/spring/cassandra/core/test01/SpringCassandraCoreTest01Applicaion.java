package org.py.spring.cassandra.core.test01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class SpringCassandraCoreTest01Applicaion {

    public static void main(String[] args) {
        SpringApplication.run(SpringCassandraCoreTest01Applicaion.class, args);
    }

}
