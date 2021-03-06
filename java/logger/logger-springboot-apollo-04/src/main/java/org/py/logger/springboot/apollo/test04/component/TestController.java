package org.py.logger.springboot.apollo.test04.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author pengyue.du
 * @Date 2020/9/11 3:02 下午
 * @Description
 */
//@Slf4j
@RestController
public class TestController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("hello")
    public String hello() {
        log.debug("hello debug");
        log.info("hello info");
        log.warn("hello warn");
        log.error("hello error");
        return "hello world";
    }

}
