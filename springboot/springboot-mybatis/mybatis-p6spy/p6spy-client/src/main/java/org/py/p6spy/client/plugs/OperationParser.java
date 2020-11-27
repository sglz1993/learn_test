package org.py.p6spy.client.plugs;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class OperationParser {

    private static List<String> readList = Lists.newArrayList("select");
    private static List<String> writeList = Lists.newArrayList("update", "insert", "delete");

    public static OperationType parse(String sql) {
        if(StringUtils.isBlank(sql)) {
            return OperationType.other;
        }
        sql = sql.trim().toLowerCase();
        if(belongTo(sql, readList)) {
            return OperationType.read;
        }
        if(belongTo(sql, writeList)) {
            return OperationType.write;
        }
        return OperationType.other;
    }

    private static boolean belongTo(String sql, List<String> list) {
        for(String head : list) {
            if (sql.startsWith(head)) {
                return true;
            }
        }
        return false;
    }

}
