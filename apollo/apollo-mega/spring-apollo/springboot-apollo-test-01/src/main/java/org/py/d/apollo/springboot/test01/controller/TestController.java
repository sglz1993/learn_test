package org.py.d.apollo.springboot.test01.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author pengyue.du
 * @Date 2020/10/20 5:40 下午
 * @Description
 */
@Slf4j
@RestController
public class TestController {

    @Value("${name}")
    private String name;

    @RequestMapping("/")
    public String hello() {
        return "hello";
    }


    @RequestMapping("/name")
    public String name() {
        return name;
    }

}
