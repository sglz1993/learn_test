package org.py.test.controller;

import org.py.test.service.LazyService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author pengyue.du
 * @Date 2020/10/16 4:09 下午
 * @Description
 */
@RestController
public class LazyController {

    @Lazy
    @Resource
    private LazyService lazyService;


    @GetMapping("lazy")
    public String hello() {
        return lazyService.lazy();
    }

}
