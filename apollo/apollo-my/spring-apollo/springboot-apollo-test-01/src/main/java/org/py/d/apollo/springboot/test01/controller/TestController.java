package org.py.d.apollo.springboot.test01.controller;

import lombok.extern.slf4j.Slf4j;
import org.py.apollo.config.Config;
import org.py.common.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    @Value("${app.id}")
    private String appID;

    @RequestMapping("/")
    public Map<String, Object> hello() {
        return Util.asMap("value", appID, "apollo", Config.getConfig("app.id"));
    }


    @RequestMapping("/name")
    public Map<String, Object> name() {
        return Util.asMap("value", name, "apollo", Config.getConfig("name"));
    }

}
