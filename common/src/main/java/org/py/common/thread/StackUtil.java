package org.py.common.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StackUtil {


    public static void printThreadStack(int maxDeap) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for(int i = 2; i < maxDeap + 2 && i < stackTrace.length; i++) {
            StackTraceElement element = stackTrace[i];
            String className = element.getClassName();
            String methodName = element.getMethodName();
            int lineNumber = element.getLineNumber();
            System.out.println(String.format("className:%s\tmethodName:%s\tlimeNumber:%s", className, methodName, lineNumber));
        }
    }

    public static void printThreadStack() {
        printThreadStack(Integer.MAX_VALUE);
    }

    public static void logThreadStack(int maxDeap) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for(int i = 2; i < maxDeap + 2 && i < stackTrace.length; i++) {
            StackTraceElement element = stackTrace[i];
            String className = element.getClassName();
            String methodName = element.getMethodName();
            int lineNumber = element.getLineNumber();
            log.info("className:{}\tmethodName:{}\tlimeNumber:{}", className, methodName, lineNumber);
        }
    }

    public static void logThreadStack() {
        logThreadStack(Integer.MAX_VALUE);
    }

}
