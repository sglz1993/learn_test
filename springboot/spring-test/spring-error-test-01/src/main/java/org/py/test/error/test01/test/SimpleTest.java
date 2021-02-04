package org.py.test.error.test01.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.helpers.MessageFormatter;

@Slf4j
public class SimpleTest {

    @Test
    public void testExcepFormatter() {
        try {
            int i = 1/0;
        } catch (Exception e) {
            System.out.println(MessageFormatter.arrayFormat("lalala", new Object[]{e}).getMessage());
            log.error("", e);
        }
    }

}
