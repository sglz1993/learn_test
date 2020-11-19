package org.py.test.jvm.gc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @Author pengyue.du
 * @Date 2020/9/21 5:12 下午
 * @Description
 */
public class LogTest {

    /**
     * -XX:+HeapDumpOnOutOfMemoryError -Xms1M -Xmx1M
     *
     *  -Xloggc:/Users/pengyue.du/Code/Meijia/Work01/learn_test/java/jvm/jvm_metrics_test_01/file/test_file/gc_log/gc-%t.log -XX:+HeapDumpOnOutOfMemoryError  -Xms1M -Xmx1M -XX:HeapDumpPath=/Users/pengyue.du/Code/Meijia/Work01/learn_test/java/jvm/jvm_metrics_test_01/file/test_file/gc_log/oom-%t.bin
     *  *****
     * -XX:HeapDumpPath=/Users/pengyue.du/Code/Meijia/Work01/learn_test/java/jvm/jvm_metrics_test_01/file/test_file/gc_log/oom-%t.bin
     *      无效：oom-%t.bin
     * -Xloggc:/Users/pengyue.du/Code/Meijia/Work01/learn_test/java/jvm/jvm_metrics_test_01/file/test_file/gc_log/gc-%t.log
     *      有效：gc-2020-09-21_17-21-19.log
     *
     */
    @Test
    public void test() {
        Object[] array = new Object[1024];
        for(int i = 0; i < array.length; i++) {
            array[i] = new Object();
            test();
        }
    }

    /**
     * 测试CMS垃圾收集器日志
     * -Xms2M -Xmx2M -XX:NativeMemoryTracking=summary -XX:CompressedClassSpaceSize=128m -XshowSettings:vm -Xss512K -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:-UseGCOverheadLimit -XX:+CMSScavengeBeforeRemark -XX:CMSInitiatingOccupancyFraction=10 -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/pengyue.du/Code/Meijia/Work01/learn_test/java/jvm/jvm_metrics_test_01/file/test_file/gc_log/oom-heap.bin -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:/Users/pengyue.du/Code/Meijia/Work01/learn_test/java/jvm/jvm_metrics_test_01/file/test_file/gc_log/gc.log
     *   -Xms1M -Xmx1M
     */
    @Test
    public void testCMSLog() {
        outOfMemoryError();
    }

    /**
     * 测试G1垃圾收集器
     * -Xms2M -Xmx2M -XX:NativeMemoryTracking=summary -XX:CompressedClassSpaceSize=28m -XshowSettings:vm -Xss512K -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/pengyue.du/Code/Meijia/Work01/learn_test/java/jvm/jvm_metrics_test_01/file/test_file/gc_log/oom-heap-g1.bin -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:/Users/pengyue.du/Code/Meijia/Work01/learn_test/java/jvm/jvm_metrics_test_01/file/test_file/gc_log/gc_g1.log
     * -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime
     */
    @Test
    public void testG1Log() {
        outOfMemoryError();
    }

    private void outOfMemoryError() {
        int i = 0;
        List<Object> list = new ArrayList<>();
        while (true){
            list.add(getString(20));
            System.gc();
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Object getString(int l) {
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0; i< l; i ++) {
            stringBuffer.append(UUID.randomUUID().toString());
        }
        return stringBuffer;
    }

    /**
     *  -XX:+UseSerialGC -XX:+PrintGCDetails
     */
    @Test
    public void  useUseSerialGC() {
        System.gc();

    }


}
