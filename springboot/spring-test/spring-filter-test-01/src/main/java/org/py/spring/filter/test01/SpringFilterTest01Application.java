package org.py.spring.filter.test01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "org.py.spring.filter")
public class SpringFilterTest01Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringFilterTest01Application.class, args);
    }

}
