package org.py.test.jvm.vm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author pengyue.du
 * @Date 2020/10/9 3:38 下午
 * @Description
 */
@Slf4j
public class RunVMParamterTest {

    @Test
    public void testFlags() {
        while (true) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
