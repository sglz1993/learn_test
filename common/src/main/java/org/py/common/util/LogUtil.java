package org.py.common.util;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.py.common.Constant;
import org.py.common.reflect.Jexl3Util;
import org.py.common.thread.ExceptionUtil;
import org.py.common.thread.StackUtil;
import org.py.common.weixin.WarningUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

import java.util.Map;

public class LogUtil {



    public static void addLoggerFilter() {
        LoggerContext loggerFactory = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerFactory.addTurboFilter(new TurboFilter() {
//System.out.println(String.format("logger.name:%s, level:%s-%s , format:%s, param:%s, exception:%s",
//        logger.getName(), level.toString(), level.toInt(), format, toString(params), ExceptionUtil.getThrowableStackInfo(t)));
            @Override
            public FilterReply decide(Marker marker, Logger logger, ch.qos.logback.classic.Level level, String format, Object[] params, Throwable t) {
                if(level.levelInt >= ch.qos.logback.classic.Level.ERROR.levelInt) {
                    WarningUtil warningUtil = CacheInjector.getInjector(WarningUtil.class);
                    String requestId = MDC.get(Constant.REQUEST_ID);
                    String message = String.format(String.format("requestId:%s  --  loggerlevel:%s   \n" +
                            "loggerName:%s\n" +
                            "msg:%s\n" +
                            "%s", requestId, level.levelStr, logger.getName(), MessageFormatter.arrayFormat(format, params, t).getMessage()),
                            getExecptionDisplay(t));
                    warningUtil.sendWarning(message);
                }
                return FilterReply.NEUTRAL;
            }

            private String getExecptionDisplay(Throwable t) {
                if(t == null) {
                    return "";
                }
                return String.format("error stack : %s", ExceptionUtil.getThrowableStackInfo(t));
            }
        });
    }

    public static void log(Level level, String format, Object... param) {
        String jexlExp = String.format("log.%s(\"%s\", \"%s\")", level.name().toLowerCase(), format, param == null || param.length == 0 ? "": param);
        Jexl3Util.evaluate(jexlExp, Util.stringMap("log", LoggerFactory.getLogger(StackUtil.getClassName(1))));
    }

    public static void println(String format, Object... param) {
        String jexlExp = String.format("log.println(\"%s\")", MessageFormatter.arrayFormat(format, param).getMessage());
        Jexl3Util.evaluate(jexlExp, Util.stringMap("log", System.out));
    }

    public static void execAndPrintln(String format, Map<String, Object> paramMap, Object... param) {
        String message = MessageFormatter.arrayFormat(format, param).getMessage();
        Object result = Jexl3Util.evaluate(message, paramMap);
        String jexlExp = String.format("log.println(\"%s \t exp: %s, result: %s\")", DateUtil.getStrDate(), MessageFormatter.arrayFormat(format, param).getMessage(), result);
        Jexl3Util.evaluate(jexlExp, Util.stringMap("log", System.out));
    }

    public static void execSimpleAndPrintln(String format, Object... param) {
        String message = MessageFormatter.arrayFormat(format, param).getMessage();
        Object result = Jexl3Util.evaluate(message, null);
        String jexlExp = String.format("log.println(\"%s \t exp: %s, result: %s\")", DateUtil.getStrDate(), MessageFormatter.arrayFormat(format, param).getMessage(), result);
        Jexl3Util.evaluate(jexlExp, Util.stringMap("log", System.out));
    }

}
