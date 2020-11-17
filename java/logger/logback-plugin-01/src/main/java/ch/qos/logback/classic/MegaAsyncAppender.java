package ch.qos.logback.classic;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AsyncAppenderBase;
import co.my.logback.util.MetricsUtil;
import co.my.logback.util.ReflectUtils;

import java.net.URL;
import java.util.concurrent.BlockingQueue;

/**
 * @Author pengyue.du
 * @Date 2020/8/20 3:18 下午
 * @Description
 */
public class myAsyncAppender extends AsyncAppender {

    private static boolean recordEnable = checkMetricsRecordEnable();

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (recordEnable) {
            if (invokeIsQueueBelowDiscardingThreshold() && isDiscardable(eventObject)) {
                return;
            }
            preprocess(eventObject);
            put(eventObject);
        }else {
            super.append(eventObject);
        }
    }

    private void put(ILoggingEvent eventObject) {
        boolean neverBlock = ReflectUtils.getFiled(AsyncAppenderBase.class, "neverBlock", this, Boolean.class);
        if (neverBlock) {
            BlockingQueue<ILoggingEvent> blockingQueue = ReflectUtils.getFiled(AsyncAppenderBase.class, "blockingQueue", this, BlockingQueue.class);
            boolean offer = blockingQueue.offer(eventObject);
            if(!offer) {
                MetricsUtil.record();
            }
        } else {
            invokePutUninterruptibly(eventObject);
        }
    }

    private boolean invokeIsQueueBelowDiscardingThreshold() {
        return (Boolean) ReflectUtils.invoke(AsyncAppenderBase.class, "isQueueBelowDiscardingThreshold", this);
    }

    private void invokePutUninterruptibly(ILoggingEvent eventObject) {
        ReflectUtils.invoke(AsyncAppenderBase.class, "putUninterruptibly", this, eventObject);
    }

    private static boolean checkMetricsRecordEnable() {
        URL counterResource = ClassLoader.getSystemResource("io/prometheus/client/Counter.class");
        return counterResource != null;
    }
}
