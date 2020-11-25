package old.tool.http.okhttp3;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: pengyue.du
 * @time: 2020/7/9 5:08 下午
 */
@Slf4j
public class HelloOkHttp3 {

    @Test
    public void get() throws Exception{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        }
    }

    @Test
    public void post() throws IOException {
        Map<String, Object> param = new HashMap<>();
        param.put("msgtype", "text");
        Map<String, Object> warning = new HashMap<>();
        warning.put("content", "小告警2");
        warning.put("mentioned_mobile_list", Lists.newArrayList("17610786672"));
        param.put("text", warning);
        post("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=d66026bf-93a5-4414-a3d8-882355ad38ce", com.alibaba.fastjson.JSON.toJSONString(param));

    }

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Test
    public void testWeixinWarning() {
        String env = "Local";
        String domain = "Test";
        String webHookAddr = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxxxx";
        boolean sync = false;
        //创建一个工具类对象，指定webhook地址等公共信息
//        WeixinUtil util = new WeixinUtil(env, domain, webHookAddr, sync);
//        //文本支持@某个人或所有人，需要指定特别提醒人的手机号
//        List<String> warningMobiles = Lists.newArrayList("xx手机号xx");
//        //发送普通文本消息
//        util.sendWarning("我是普通的文本消息");
//        //发送特别提醒某人的文本消息
//        util.sendWarning("我是带 @xxx 的普通的文本消息", warningMobiles);
//        //使用默认模板发送Markdown消息
//        util.sendWarningMD("我是一个普通的Markdown消息");
//        String template = "### {TITLE_MESSAGE}";
//        Map<String, String> placeHolder = Util.asMap("TITLE_MESSAGE", "我是一个三级标题消息");
//        //自定义模板发送到指定webhook地址
//        util.sendWarningByTemplateMD(webHookAddr, template, placeHolder);
    }




}
