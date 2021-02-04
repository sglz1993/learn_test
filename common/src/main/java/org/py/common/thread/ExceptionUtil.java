package org.py.common.thread;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    public static String getThrowableStackInfo(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

}
