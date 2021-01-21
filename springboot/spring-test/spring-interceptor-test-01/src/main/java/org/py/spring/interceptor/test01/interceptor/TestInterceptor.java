package org.py.spring.interceptor.test01.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * org.springframework.web.servlet.HandlerExecutionChain#applyPreHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 *
 * org.springframework.web.servlet.HandlerInterceptor
 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter
 * HandlerInterceptorAdapter 只提供了HandlerInterceptor的空方法，两者一样
 *
 */
@Slf4j
public class TestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        if (requestURI.equals("/") || requestURI.equals("/hello")) {
            return 10/0 == 1;
        }
        return true;
//        return true;
//        return false;
    }
}
