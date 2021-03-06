package org.py.logback.filter.test01.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import com.google.common.collect.Lists;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

/**
 * 消息格式化参考：https://www.jianshu.com/p/f91ef6aa6a85
 */
public class HelloLogbackFilter extends TurboFilter {

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
//        System.out.println(String.format("logger.name:%s, level:%s-%s , format:%s, param:%s, exception:%s",
//                logger.getName(), level.toString(), level.toInt(), format, toString(params), ExceptionUtil.getThrowableStackInfo(t)));
//        System.out.println(MessageFormatter.arrayFormat(format, Stream.of(params)
//                .map(JSON::toJSONString).toArray()).getMessage());
        System.out.println(MessageFormatter.arrayFormat(format, params, t).getMessage());
        return FilterReply.NEUTRAL;
    }

    private <T> String toString(T[] arrays) {
        if(arrays == null || arrays.length == 0) {
            return "";
        }
        return Lists.newArrayList(arrays).toString();
    }

}
