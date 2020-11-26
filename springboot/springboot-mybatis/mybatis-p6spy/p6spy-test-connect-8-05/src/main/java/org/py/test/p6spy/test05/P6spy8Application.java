package org.py.test.p6spy.test05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 */
@EnableTransactionManagement
@SpringBootApplication
public class P6spy8Application {

    public static void main(String[] args) {
        SpringApplication.run(P6spy8Application.class, args);
    }

}
