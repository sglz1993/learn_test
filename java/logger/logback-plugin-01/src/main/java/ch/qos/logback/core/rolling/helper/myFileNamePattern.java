package ch.qos.logback.core.rolling.helper;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.pattern.Converter;
import ch.qos.logback.core.pattern.LiteralConverter;

import java.util.Date;

/**
 * @Author pengyue.du
 * @Date 2020/8/18 5:47 pm
 * @Description
 */
public class myFileNamePattern extends FileNamePattern   {

    public myFileNamePattern(String patternArg, Context contextArg) {
        super(patternArg, contextArg);
    }

    @Override
    public String toRegexForFixedDate(Date date) {
        StringBuilder buf = new StringBuilder();
        Converter<Object> p = headTokenConverter;
        while (p != null) {
            if (p instanceof LiteralConverter) {
                buf.append(p.convert(null));
            } else if (p instanceof IntegerTokenConverter) {
                buf.append("(\\d{1,8})");
            } else if (p instanceof DateTokenConverter) {
                buf.append(p.convert(date));
            }
            p = p.getNext();
        }
        return buf.toString();
    }

}
