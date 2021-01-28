package org.py.test.log_hello.exec.code;

import org.apache.commons.jexl3.*;
import org.apache.commons.jexl3.internal.Engine;
import org.junit.Test;
import org.py.common.reflect.Jexl3Util;
import org.py.common.util.LogUtil;
import org.py.common.util.Util;
import org.py.test.log_hello.LogMethod;
import org.slf4j.event.Level;

/**
 * 参考：https://blog.csdn.net/qq_26954773/article/details/80379015
 * maven： https://mvnrepository.com/artifact/org.apache.commons/commons-jexl3
 * 官网：https://commons.apache.org/proper/commons-jexl/index.html
 */
public class Jexl3Test {

    @Test
    public void test3() {
        Object evaluate = Jexl3Util.evaluate("3 + 5", null);
        LogUtil.log(Level.INFO, "3 + 5");
        LogUtil.println("3 + 5");
        LogUtil.execAndPrintln("3 + 5", null);
        LogUtil.execSimpleAndPrintln("3 + 5");
    }

    /**
     * hello jexl
     */
    @Test
    public void test2() {
        String jexlExp = "sysout.println(\"%s\")";

        JexlEngine jexlEngine = new JexlBuilder().create();
        JexlExpression expression = jexlEngine.createExpression(String.format(jexlExp, "hello jexl"));
        JexlContext context = new MapContext();
        Util.stringMap("sysout", System.out).forEach(context::set);
        expression.evaluate(context);
    }

    /**
     * org.apache.commons.jexl3.JexlException$Variable: org.py.test.log_hello.exec.code.Jexl3Test.test1@1:1 undefined variable java.lang.System.out
     */
    @Test
    public void test1() {
        String jexlExp = "java.lang.System.out.println(\"%s\")";

        JexlEngine jexlEngine = new JexlBuilder().create();
        JexlExpression expression = jexlEngine.createExpression(String.format(jexlExp, "hello jexl"));
        JexlContext context = new MapContext();
        expression.evaluate(context);
    }

    /**
     * 参考的博客示例，其实两个一样，区别创建引擎方式不同
     */
    @Test
    public void testHello2() {
        String jexlExp = "foo.test1()";

        JexlEngine jexlEngine = new Engine();
        JexlExpression expression = jexlEngine.createExpression(jexlExp);
        JexlContext context = new MapContext();
        context.set("foo", new LogMethod());
        expression.evaluate(context);
    }

    /**
     * 官方示例
     */
    @Test
    public void testHello1() {
        // Create or retrieve an engine
        JexlEngine jexl = new JexlBuilder().create();

        // Create an expression
        String jexlExp = "foo.test1()";
        JexlExpression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("foo", new LogMethod());

        // Now evaluate the expression, getting the result
        Object o = e.evaluate(jc);
    }

}
