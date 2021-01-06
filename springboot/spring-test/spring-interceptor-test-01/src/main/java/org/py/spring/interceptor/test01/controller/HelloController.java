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

}
