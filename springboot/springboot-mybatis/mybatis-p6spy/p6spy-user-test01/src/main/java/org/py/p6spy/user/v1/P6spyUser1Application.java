package org.py.p6spy.user.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 */
@EnableTransactionManagement
@SpringBootApplication
public class P6spyUser1Application {

    public static void main(String[] args) {
        SpringApplication.run(P6spyUser1Application.class, args);
    }

}
