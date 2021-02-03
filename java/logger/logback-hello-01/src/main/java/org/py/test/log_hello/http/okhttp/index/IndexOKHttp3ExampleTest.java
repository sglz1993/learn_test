package org.py.test.log_hello.http.okhttp.index;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;

@Slf4j
public class IndexOKHttp3ExampleTest {


    @Test
    public void testGet() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://baidu.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            log.info(string);
        }
    }

    @Test
    public void testPost() throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create("jsonparam", JSON);
        Request request = new Request.Builder()
                .url("https://baidu.com")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            log.info(string);
        }
    }

//    OkHttpClient client = new OkHttpClient.Builder()
//            .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
//            .build();

    @Test
    public void testCertificate() throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
                .certificatePinner(
                        new CertificatePinner.Builder()
                                .add("publicobject.com", "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=")
                                .build())
                .build();

        Request request = new Request.Builder()
                .url("https://publicobject.com/robots.txt")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            for (Certificate certificate : response.handshake().peerCertificates()) {
                System.out.println(CertificatePinner.pin(certificate));
            }
        }
    }


    @Test
    public void testCustomCertificate() throws Exception {
        X509TrustManager trustManager = null;
        SSLSocketFactory sslSocketFactory;
        {
            try {
//                trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[] { trustManager }, null);
                sslSocketFactory = sslContext.getSocketFactory();
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build();
        Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    /**
     * 应用拦截器和网络拦截器区别：https://square.github.io/okhttp/interceptors/
     *
     * 应用拦截器
     *
         * 无需担心中间响应，例如重定向和重试。
         * 即使从缓存提供HTTP响应，也总是被调用一次。
         * 遵守应用程序的原始意图。不关心OkHttp注入的标头，例如If-None-Match。
         * 允许短路而不是Chain.proceed()。
         * 允许重试并多次致电Chain.proceed()。
         * 可以使用withConnectTimeout，withReadTimeout，withWriteTimeout调整呼叫超时。
     * 网络拦截器
     *
         * 能够对重定向和重试之类的中间响应进行操作。
         * 不会为使网络短路的缓存响应调用。
         * 观察数据，就像通过网络传输数据一样。
         * 访问Connection带有请求的。
     *
     * 注：先执行应用interceptor,后执行networkInterceptor，先添加的先执行，在外层
     */
    @Test
    public void testInterceptor() throws IOException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(chain -> {
            log.info("addNetworkInterceptor  1  start");
            Response response = chain.proceed(chain.request());
            log.info("addNetworkInterceptor  1  end");
            return response;
        }).addInterceptor(chain -> {
            log.info("addInterceptor  1  start");
            Request request = chain.request();
            Call call = chain.call();
            Response response = chain.proceed(chain.request());
            log.info("addInterceptor  1  end");
            int code = response.code();
            return response;
        }).addNetworkInterceptor(chain -> {
            log.info("addNetworkInterceptor  2  start");
            Response response = chain.proceed(chain.request());
            log.info("addNetworkInterceptor  2  end");
            return response;
        }).addInterceptor(chain -> {
            log.info("addInterceptor  2  start");
            Request request = chain.request();
            Call call = chain.call();
            Response response = chain.proceed(chain.request());
            log.info("addInterceptor  2  end");
            int code = response.code();
            return response;
        });
        OkHttpClient client = builder.build();
        Request request = new Request.Builder().url("https://www.baidu.com").build();
        Response response = client.newCall(request).execute();
    }





}
