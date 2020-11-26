package org.py.p6spy.version01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 */
@EnableTransactionManagement
@SpringBootApplication
public class P6spyVersion1Application {

    public static void main(String[] args) {
        SpringApplication.run(P6spyVersion1Application.class, args);
    }

}
