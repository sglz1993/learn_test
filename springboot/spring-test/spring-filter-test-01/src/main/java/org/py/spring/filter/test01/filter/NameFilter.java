package org.py.spring.filter.test01.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class NameFilter implements Filter {

    private String name;

    public NameFilter(String name) {
        this.name = name;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("{} filter:{}", name, ((HttpServletRequest)request).getRequestURI());
        chain.doFilter(request, response);
    }
}
