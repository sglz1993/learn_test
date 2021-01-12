package org.py.logback.filter.test01.filter;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import com.google.common.collect.Lists;
import org.slf4j.Marker;

public class HelloLogbackFilter extends TurboFilter {

    @Override
    public FilterReply decide(Marker marker, Logger logger, Level level, String format, Object[] params, Throwable t) {
        System.out.println(String.format("logger.name:%s, level:%s-%s , format:%s, param:%s",
                logger.getName(), level.toString(), level.toInt(), format, Lists.newArrayList(params).toString()));
        return FilterReply.NEUTRAL;
    }

}
