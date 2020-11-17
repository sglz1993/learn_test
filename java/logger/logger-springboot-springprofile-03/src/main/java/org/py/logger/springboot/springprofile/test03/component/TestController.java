package org.py.logger.springboot.springprofile.test03.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author pengyue.du
 * @Date 2020/9/11 3:02 下午
 * @Description
 */
@Slf4j
@RestController
public class TestController {

    @RequestMapping("hello")
    public String hello() {
        log.info("hello lalal");
        return "hello world";
    }

}
