package org.py.test;

import org.junit.Test;
import org.py.tool.weixin.WeixinUtil;

public class WeixinUtilTest {

    String webHookAddr = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=92d21cbd-fc09-451a-8527-4c332d60b4c8";


    @Test
    public void test() {
        WeixinUtil weixinUtil = new WeixinUtil("dev", "local", webHookAddr, true);
        weixinUtil.sendWarning("\n lalalsdfsdfsfffffflalallalalffflalalsdfsdfsfffffflalallalalffffflalalffffffffffffflalalsdfsdfsfffffflalallalalffffflalalffffffffffffffflalalfffffffffffff; \n 2sdffffflalallalallalallalallalallalallalallalalfffffffffffffffff; \n 4dsffffffffffffffffffffffffffffffffff");
//        weixinUtil.sendWarningMD("test");
    }

}
