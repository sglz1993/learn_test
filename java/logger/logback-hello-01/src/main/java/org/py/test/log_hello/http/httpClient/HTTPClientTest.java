package org.py.test.log_hello.http.httpClient;

import org.junit.Test;
import org.py.test.log_hello.http.httpClient.baiyi.HttpClientUtilV1;

public class HTTPClientTest {

    /**
     * java.lang.RuntimeException: org.apache.http.conn.HttpHostConnectException: Connect to 127.0.0.1:9019 [/127.0.0.1] failed: Connection refused (Connection refused)
     */
    @Test
    public void testConnectionTimeout() {
        try {
            String s = HttpClientUtilV1.get("http://127.0.0.1:9019", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * java.lang.RuntimeException: java.net.SocketTimeoutException: Read timed out
     *
     * 参考：https://www.cnblogs.com/nuccch/p/10611877.html
     * 实际：HttpClientBuilder builder = HttpClients.custom().setDefaultSocketConfig(SocketConfig.copy(SocketConfig.DEFAULT).setSoTimeout(5000).build());
     */
    @Test
    public void testReadTimeout() {
        try {
            String s = HttpClientUtilV1.get("http://127.0.0.1:8082/api/1/dolphin/status", null);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
