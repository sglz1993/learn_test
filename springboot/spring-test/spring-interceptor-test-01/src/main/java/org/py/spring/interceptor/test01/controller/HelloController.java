package org.py.spring.interceptor.test01.controller;

import org.py.common.site.SiteUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public Object hello() {
        return SiteUtil.nowSite();
    }

    @RequestMapping("/hello")
    public Object hello2() {
        return SiteUtil.nowSite();
    }

    @RequestMapping("/my404NotFound")
    public Object my404NotFound() {
        return ":my404NotFound";
    }

    @RequestMapping("/error")
    public Object error() {
        return 10/0;
    }

}
