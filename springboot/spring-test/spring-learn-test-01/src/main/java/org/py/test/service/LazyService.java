package org.py.test.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @Author pengyue.du
 * @Date 2020/10/16 4:10 下午
 * @Description
 */
@Service
@Lazy
public class LazyService {

    public LazyService() {
        System.out.println("****************************lazy init****************************");
    }

    public String lazy() {
        return "lazy service";
    }

}
