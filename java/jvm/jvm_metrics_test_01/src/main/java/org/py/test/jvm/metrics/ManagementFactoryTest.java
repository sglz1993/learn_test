package org.py.test.jvm.metrics;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

/**
 * @Author pengyue.du
 * @Date 2020/9/16 2:31 下午
 * @Description
 */
@Slf4j
public class ManagementFactoryTest {

    @Test
    public void test01() {
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getPlatformMXBeans(MemoryPoolMXBean.class);
        for(MemoryPoolMXBean mxBean: memoryPoolMXBeans) {
            log.info("name:{} , type:{} , max:{}, committed:{}, init:{}, used:{}", mxBean.getName(), mxBean.getType(), mxBean.getUsage().getMax(), mxBean.getUsage().getCommitted(), mxBean.getUsage().getInit(), mxBean.getUsage().getUsed());
        }
    }

}
