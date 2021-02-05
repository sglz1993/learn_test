package org.py.test.log_hello.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

@Slf4j
public class SimpleTest {

    @Test
    public void test6() throws IOException, URISyntaxException {
        String urls = "https://mobile.ximalaya.com/passport-sign-mobile/qrcode/login/0867C4572F9E4D8FA65F01D40D15473D/qrcode-7172636f6465?to=openapi";
        URL url = new URL(urls);
        String authority = url.getAuthority();
        Object content = url.getContent();
        int defaultPort = url.getDefaultPort();
        String file = url.getFile();
        String host = url.getHost();
        String path = url.getPath();
        int port = url.getPort();
        String protocol = url.getProtocol();
        String query = url.getQuery();
        String ref = url.getRef();
        String userInfo = url.getUserInfo();
        int i = url.hashCode();
        String s = url.toExternalForm();
        URI uri = url.toURI();
        String authority1 = uri.getAuthority();
        String fragment = uri.getFragment();
        String host1 = uri.getHost();
        String path1 = uri.getPath();
        String query1 = uri.getQuery();
        System.out.println("8888888888888888888");
    }

    @Test
    public void test5() {
        //随机生成长度为10-32位的字符串
        String s = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(10, 32));
    }

    @Test
    public void test4() {
        String s = Integer.toString((1 & 0xff) + 0x100, 16);
        System.out.println(s);
    }

    @Test
    public void test3() {
        Object intarr = new int[]{};
        Object intvalue = 1;
        Object bvalue = ',';
        Object bytearr = new byte[]{};
        Object longarr = new long[]{};
        Object longvalue = 1L;
        Object barr = new boolean[]{};
        Object booleanvalue = true;
//        System.out.println();
//        LogUtil.execSimpleAndPrintln("bytearr instanceof byte[]", Util.stringMap("bytearr", bytearr));
        System.out.println(byte.class.getName() + "\t"+ (bvalue instanceof byte[]));
        System.out.println(int[].class.getName() + "\t"+ (intarr instanceof byte[]));
        System.out.println(int.class.getName() + "\t"+ (intvalue instanceof byte[]));
        System.out.println(long[].class.getName() + "\t"+ (longarr instanceof byte[]));
        System.out.println(long.class.getName() + "\t"+ (longvalue instanceof byte[]));
        System.out.println(boolean[].class.getName() + "\t"+ (barr instanceof byte[]));
        System.out.println(boolean.class.getName() + "\t"+ (booleanvalue instanceof byte[]));
        System.out.println(((byte[])bytearr).length);
    }

    @Test
    public void test2() {
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm ZZZZ");
        System.out.println(formatter.format(zdt));

        DateTimeFormatter zhFormatter = DateTimeFormatter.ofPattern("yyyy MMM dd EE HH:mm ZZZZ", Locale.CHINA);
        System.out.println(zhFormatter.format(zdt));

        DateTimeFormatter usFormatter = DateTimeFormatter.ofPattern("E, MMMM/dd/yyyy HH:mm", Locale.US);
        System.out.println(usFormatter.format(zdt));
    }

    @Test
    public void test01() {
        System.out.println("test2020121612345test20201216123".length());
        String subject = "1vID:test2020121612345test20201216123/env:dev";
        Pattern pattern = compile(".*vID:([0-9a-z]{32})/env:([a-z]{3,4})");
        Matcher matcher = pattern.matcher(subject);
        System.out.println(matcher.find());
    }

}
