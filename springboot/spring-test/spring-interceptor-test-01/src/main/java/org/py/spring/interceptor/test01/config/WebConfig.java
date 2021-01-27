package org.py.spring.interceptor.test01.config;

import lombok.extern.slf4j.Slf4j;
import org.py.spring.interceptor.test01.interceptor.TestInterceptor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new TestInterceptor());
        if(false) {
            interceptorRegistration.addPathPatterns("/");
        }
        registry.addWebRequestInterceptor(testWebRequestInterceptor());
        registry.addInterceptor(testHandlerInterceptor2());
    }

    @Bean
    public WebServerFactoryCustomizer errorPageMy(ServerProperties serverProperties) {
        return (WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>) factory -> factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/my404NotFound"));
    }

    public WebRequestInterceptor testWebRequestInterceptor() {
        return new WebRequestInterceptor() {
            @Override
            public void preHandle(WebRequest request) throws Exception {
                log.info("WebRequestInterceptor test, uri:{}", ((ServletWebRequest)request).getRequest().getRequestURI());
            }

            @Override
            public void postHandle(WebRequest request, ModelMap model) throws Exception {

            }

            @Override
            public void afterCompletion(WebRequest request, Exception ex) throws Exception {

            }
        };
    }

    public HandlerInterceptor testHandlerInterceptor2() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                log.info("testHandlerInterceptor2 uri:{}", request.getRequestURI());
                return true;
            }
        };
    }

//    /**
//     * 更好的配置方法
//     * @return
//     */
//    @Bean
//    public ErrorPageRegistrar error404Page() {
//        return new ErrorPageRegistrar() {
//
//            @Override
//            public void registerErrorPages(ErrorPageRegistry registry) {
//                ErrorPage errorPage = new ErrorPage(HttpStatus.NOT_FOUND, "/my404NotFound");
//                registry.addErrorPages(errorPage);
//            }
//        };
//    }

}
