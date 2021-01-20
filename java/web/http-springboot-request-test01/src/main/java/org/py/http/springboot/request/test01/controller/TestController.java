package org.py.http.springboot.request.test01.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author pengyue.du
 * @Date 2020/9/11 3:02 下午
 * @Description
 */
@RestController
public class TestController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("hello")
    public String hello() {
        return "hello world";
    }

}
