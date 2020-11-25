package old.tool.http.common;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author: pengyue.du
 * @time: 2020/7/28 10:49 上午
 */
public class HttpCommonTest {

    @Test
    public void test() throws MalformedURLException {
        URL url = new URL(getSpec());
        System.out.println(url.getHost());
        System.out.println(url.getProtocol());
        System.out.println(url.getQuery());
        System.out.println(url.getPath());
        System.out.println(url.getPath());
        String host = String.format("%s://%s", url.getProtocol(), url.getHost());
        System.out.println(host);
        System.out.println(getSpec().substring(host.length()));
        System.out.println(host + getSpec().substring(host.length()));
        System.out.println(getSpec().equals(host+getSpec().substring(host.length())));
    }

    @NotNull
    private String getSpec() {
        return "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=d66026bf-93a5-4414-a3d8-882355ad38ce";
    }

}
