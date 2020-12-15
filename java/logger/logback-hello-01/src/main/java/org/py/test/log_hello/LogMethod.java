package org.py.test.log_hello;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * method 好像无法变更
 *
 * @author: pengyue.du
 * @time: 2020/7/9 3:28 下午
 */
public class LogMethod {


    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LogMethod.class);
        logger.info("Hello World");
    }

    @Test
    public void test1() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
        LocalDateTime lastHour = LocalDateTime.now().minusHours(1);
        System.out.println(lastHour.format(dateTimeFormatter));
    }




}
