package org.py.test.log_hello.test;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class SimpleTest {

    @Test
    public void test01() {
        System.out.println("test2020121612345test20201216123".length());
        String subject = "1vID:test2020121612345test20201216123/env:dev";
        Pattern pattern = compile(".*vID:([0-9a-z]{32})/env:([a-z]{3,4})");
        Matcher matcher = pattern.matcher(subject);
        System.out.println(matcher.find());
    }

}