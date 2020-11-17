package org.py.test.jvm.gc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author pengyue.du
 * @Date 2020/9/16 4:19 下午
 * @Description
 */
@Slf4j
public class CollectionTest {

    /**
     *  -XX:+UnlockExperimentalVMOptions -XX:+UseZGC
     *  JDK11 开始支持
     */
    @Test
    public void testCheckUseZGC() {

    }

    /**
     * -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
     * -XX:+PrintTenuringDistribution -XX:+PrintFlagsFinal
     */
    @Test
    public void testCMS() {
//        while (true) {
//            new CollectionTest();
//        }
    }

    /**
     * -server -XX:NativeMemoryTracking=summary  -XX:MaxRAMPercentage=50.0  -XX:CompressedClassSpaceSize=128m  -XshowSettings:vm -Xss512K  -XX:+UseConcMarkSweepGC  -XX:+UseParNewGC -XX:-UseGCOverheadLimit -XX:+CMSScavengeBeforeRemark  -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=log/oom.bin  -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -Xloggc:log/gc.log
     */
    @Test
    public void testCMSmy() {
//        while (true) {
//            new CollectionTest();
//        }
    }

    /**
     * -Xmx1G -Xms1G -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
     */
    @Test
    public void testG1() {
        while (true) {
            Integer[] intArray = new Integer[1024];
        }
    }

}
