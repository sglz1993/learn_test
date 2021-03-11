package org.py.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static  <T> String toString(T[] arrays) {
        if(arrays == null || arrays.length == 0) {
            return "";
        }
        return Lists.newArrayList(arrays).toString();
    }

    public static String hump2Line(String data) {
        if(StringUtils.isBlank(data)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if(data.trim().charAt(0) < 'a') {
            data = Character.toLowerCase(data.trim().charAt(0)) + data.substring(1);
        }
        for(char c : data.trim().toCharArray()) {
            if(c >= 'a') {
                sb.append(c);
            }else {
                sb.append("_").append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    public static String line2Hump(String data) {
        if(StringUtils.isBlank(data)) {
            return "";
        }
        data = trim(data.trim(), '_');
        StringBuilder sb = new StringBuilder();
        boolean nextUpper = false;
        for(char c : data.toCharArray()) {
            if(c == '_') {
                nextUpper = true;
            }else {
                if(nextUpper) {
                    sb.append(Character.toUpperCase(c));
                    nextUpper = false;
                }else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    private static String trim(String data, char c) {
        char[] value = data.toCharArray();
        int len = value.length;
        int st = 0;
        char[] val = value;
        while ((st < len) && (val[st] == c)) {
            st++;
        }
        while ((st < len) && (val[len - 1] == c)) {
            len--;
        }
        return ((st > 0) || (len < value.length)) ? data.substring(st, len) : data;
    }

    public static void main(String[] args) {
        System.out.println("infotainment:" + hump2Line("refreshOrderStatus"));
    }

}
