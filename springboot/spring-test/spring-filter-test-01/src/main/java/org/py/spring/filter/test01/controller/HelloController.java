package org.py.spring.filter.test01.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author pengyue.du
 * @Date 2020/10/12 3:48 下午
 * @Description
 */
@RestController
public class HelloController {

    public HelloController() {
        System.out.println("****************************hello controller init****************************");
    }

    /**
     * Caused by: java.lang.IllegalArgumentException: Could not resolve placeholder 'undefindParam' in value "${undefindParam}"
     */
//    @Value("${undefindParam}")
//    private String undefindParam;

    @Value("${server.port}")
    private String port;

    @Value("${undefindParam_defaultvalue:default}")
    private String undefindParam_default;

    @GetMapping("hello")
    public String hello() {
        return "hello" + port + " \t" + undefindParam_default;
    }

}
