package org.py.spring.interceptor.test01.config;

import org.py.spring.interceptor.test01.interceptor.TestInterceptor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new TestInterceptor());
        if(false) {
            interceptorRegistration.addPathPatterns("/");
        }
    }

    @Bean
    public WebServerFactoryCustomizer errorPageMy(ServerProperties serverProperties) {
        return (WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>) factory -> factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/my404NotFound"));
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
