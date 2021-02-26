package org.py.logger.springboot.springprofile.test03.test;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class LoggerTest {

    @Test
    public void testJDKLogger() {
        Logger logger = Logger.getLogger("lalal");
        logger.info("jdk logger info");
        org.slf4j.Logger logger1 = LoggerFactory.getLogger(this.getClass());
        logger1.info("slf4j logger info");
    }

}
