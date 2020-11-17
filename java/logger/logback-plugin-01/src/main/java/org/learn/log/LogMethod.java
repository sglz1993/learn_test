package org.learn.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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





}
