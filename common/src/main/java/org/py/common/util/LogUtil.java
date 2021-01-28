package org.py.common.util;

import org.py.common.reflect.Jexl3Util;
import org.py.common.thread.StackUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.slf4j.helpers.MessageFormatter;

import java.util.Map;

public class LogUtil {

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
