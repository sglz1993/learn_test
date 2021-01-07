package org.py.spring.filter.test01.filter;

import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "myWebFilter", urlPatterns = "/*")
public class WebFilterTest extends NameFilter {


    public WebFilterTest() {
        super("myWebFilter");
    }
}
