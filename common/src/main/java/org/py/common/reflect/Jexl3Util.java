package org.py.common.reflect;

import org.apache.commons.jexl3.*;

import java.util.Map;

/**
 * 说明：
 *  1. jexl 不支持 instanceof
 */
public class Jexl3Util {

    private static final JexlEngine jexlEngine = new JexlBuilder().create();

    public static <V> V evaluate(String jexlExp, Map<String, Object> param) {
        JexlExpression expression = jexlEngine.createExpression(jexlExp);
        JexlContext context = new MapContext();
        if (param != null) {
            param.forEach(context::set);
        }
        return (V) expression.evaluate(context);
    }

}
