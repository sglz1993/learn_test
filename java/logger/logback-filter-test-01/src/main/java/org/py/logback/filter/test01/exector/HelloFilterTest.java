package org.py.logback.filter.test01.exector;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.py.common.util.LogUtil;
import org.py.logback.filter.test01.filter.HelloLogbackFilter;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

@Slf4j
public class HelloFilterTest {

    /**
     * logger.name:org.py.logback.filter.test01.exector.HelloFilterTest, level:DEBUG-10000 , format:lalal debug:{}, {}, param:[debug, 1]
     * logger.name:org.py.logback.filter.test01.exector.HelloFilterTest, level:INFO-20000 , format:lalal info:{}, {}, param:[info, 2]
     * [2021-01-12 17:47:45.483+0800] [INFO ] [] [org.py.logback.filter.test01.exector.HelloFilterTest] [test]:17 lalal info:info, 2
     * logger.name:org.py.logback.filter.test01.exector.HelloFilterTest, level:WARN-30000 , format:lalal warn:{}, {}, param:[warn, 3]
     * [2021-01-12 17:47:45.487+0800] [WARN ] [] [org.py.logback.filter.test01.exector.HelloFilterTest] [test]:18 lalal warn:warn, 3
     * logger.name:org.py.logback.filter.test01.exector.HelloFilterTest, level:ERROR-40000 , format:lalal error:{}, {}, param:[error, 4]
     * [2021-01-12 17:47:45.488+0800] [ERROR] [] [org.py.logback.filter.test01.exector.HelloFilterTest] [test]:19 lalal error:error, 4
     */
    @Test
    public void test() {
        LoggerContext loggerFactory = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerFactory.addTurboFilter(new HelloLogbackFilter());
        log.debug("lalal debug:{}, {}", "debug", 1);
        log.info("lalal info:{}, {}", "info", 2);
        log.warn("lalal warn:{}, {}", "warn", 3);
        log.error("lalal error:{}, {}", "error", 4);
    }

    /**
     * logger.name:org.py.logback.filter.test01.exector.HelloFilterTest, level:INFO-20000 , format:, param:, exception:
     * [2021-03-11 16:38:25.942+0800] [INFO ] [] [org.py.logback.filter.test01.exector.HelloFilterTest] [testError]:41
     * logger.name:org.py.logback.filter.test01.exector.HelloFilterTest, level:INFO-20000 , format:lalal, param:, exception:
     * [2021-03-11 16:38:25.947+0800] [INFO ] [] [org.py.logback.filter.test01.exector.HelloFilterTest] [testError]:42 lalal
     * logger.name:org.py.logback.filter.test01.exector.HelloFilterTest, level:INFO-20000 , format:lalal:{}, param:[param], exception:
     * [2021-03-11 16:38:25.956+0800] [INFO ] [] [org.py.logback.filter.test01.exector.HelloFilterTest] [testError]:43 lalal:param
     * logger.name:org.py.logback.filter.test01.exector.HelloFilterTest, level:ERROR-40000 , format:, param:, exception:java.lang.ArithmeticException: / by zero
     * 	at org.py.logback.filter.test01.exector.HelloFilterTest.testError(HelloFilterTest.java:45)
     * 	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:53)
     *
     * [2021-03-11 16:38:25.962+0800] [ERROR] [] [org.py.logback.filter.test01.exector.HelloFilterTest] [testError]:47
     * java.lang.ArithmeticException: / by zero
     * 	at org.py.logback.filter.test01.exector.HelloFilterTest.testError(HelloFilterTest.java:45)
     * 	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:53)
     *
     * [2021-03-11 16:38:25.962+0800] [ERROR] [] [org.py.logback.filter.test01.exector.HelloFilterTest] [testError]:48 lalal
     * java.lang.ArithmeticException: / by zero
     * 	at org.py.logback.filter.test01.exector.HelloFilterTest.testError(HelloFilterTest.java:45)
     */
    @Test
    public void testError() {
//        LoggerContext loggerFactory = (LoggerContext) LoggerFactory.getILoggerFactory();
//        loggerFactory.addTurboFilter(new HelloLogbackFilter());
        LogUtil.addLoggerFilter();
        log.info("");
        log.info("lalal");
        log.info("lalal:{}", "param");
        log.info("lalal:{}", "param", new RuntimeException("lalal"));
        try {
            int i = 1/0;
        } catch (Exception e) {
            log.error("", e);
            log.error("lalal", e);
            log.error("lalal:{}", "errormsg",  e);
        }
    }

    @Test
    public void testLogbackFilter() {
        LoggerContext loggerFactory = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerFactory.addTurboFilter(new TurboFilter() {
            @Override
            public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
                String msg = MessageFormatter.arrayFormat(format, params).getMessage();
                return FilterReply.NEUTRAL;
            }
        });
    }

    /**
     * MessageFormatter.arrayFormat(format, params, t).getMessage() 当前不支持参数为空，并且异常处理有问题
     */
    @Test
    public void testExcepFormatter() {
        LoggerContext loggerFactory = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerFactory.addTurboFilter(new HelloLogbackFilter());
        try {
            int i = 1/0;
        } catch (Exception e) {
            System.out.println(MessageFormatter.arrayFormat("lalala", new Object[]{e}).getMessage());
            log.error("", e);
        }
    }

}
