package org.py.test.log_hello.http.okhttp;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * <!-- https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp -->
 * <dependency>
 *     <groupId>com.squareup.okhttp3</groupId>
 *     <artifactId>okhttp</artifactId>
 *     <version>4.9.0</version>
 * </dependency>
 */
@SuppressWarnings("All")
@Slf4j
public class OKHttp3Test {

    /**
     * 实现异步，获取时未执行完阻塞，等待结果
     * @throws IOException
     */
    @Test
    public void testAsyncExec() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://localhost:8070/openapi/v1/apps")
                .build();
        Call call = client.newCall(request);
//        System.out.println(call.execute().body().string());
        OKHttp3UtilV1.Resp resp = new OKHttp3UtilV1.Resp(call);
        log.info(resp.getResult());
    }

    /**
     * java.net.ConnectException: Failed to connect to /127.0.0.1:9019
     *
     * Caused by: java.net.ConnectException: Connection refused (Connection refused)
     */
    @Test
    public void testConnectionTimeout() {
        try {
            String s = get("http://127.0.0.1:9019");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * java.net.UnknownHostException: www.luanqibazaolalal: nodename nor servname provided, or not known
     */
    @Test
    public void testConnectionTimeoutV2() {
        try {
            String s = get("http://www.luanqibazaolalal:9019");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * java.net.SocketTimeoutException: timeout
     * Caused by: java.net.SocketTimeoutException: Read timed out
     */
    @Test
    public void testReadTimeout() {
        try {
            String s = get("http://127.0.0.1:8082/api/1/dolphin/status");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 示例： https://www.baeldung.com/okhttp-timeouts
//     * 其实只是配置了读超时
//     */
//    @Test
//    public void testSingleRequestTimeout() {
//        OkHttpClient defaultClient = new OkHttpClient.Builder()
//                .readTimeout(1, TimeUnit.SECONDS)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://httpbin.org/delay/2")
//                .build();
//
//        Throwable thrown = catchThrowable(() -> defaultClient.newCall(request).execute());
//
//        assertThat(thrown).isInstanceOf(InterruptedIOException.class);
//
//        OkHttpClient extendedTimeoutClient = defaultClient.newBuilder()
//                .readTimeout(5, TimeUnit.SECONDS)
//                .build();
//
//        Response response = extendedTimeoutClient.newCall(request).execute();
//        assertThat(response.code()).isEqualTo(200);
//    }


    private String get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS)
                .build();
        client.newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().toString();
        }
    }

}
