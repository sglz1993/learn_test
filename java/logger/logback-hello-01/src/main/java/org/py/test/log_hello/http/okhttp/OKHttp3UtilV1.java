package org.py.test.log_hello.http.okhttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 第一版需求整理：
 * 通过 OkHttpClient.Builder 构建 OkHttpClient
 * 设置 连接、读、写 超时时间
 * 通过拦截器记录 请求响应日志、访问时间、异常重试
 * get/post 提供阻塞和异步非阻塞方案
 * json/MultipartBody(流、文件、kv)
 * 响应缓存
 */
public class OKHttp3UtilV1 {

    public static String postJson(String baseUrl, Map<String, Object> bodyParamMap) {
        return post(baseUrl, null, null, bodyParamMap, true);
    }

    public static String post(String baseUrl, Map<String, Object> bodyParamMap) {
        return post(baseUrl, null, null, bodyParamMap, false);
    }

    public static String get(String baseUrl, Map<String, Object> paramMap) {
        return get(baseUrl, null, paramMap);
    }

    public static String post(String baseUrl, Map<String, Object> headerMap, Map<String, Object> urlParamMap, Map<String, Object> bodyParamMap, boolean json) {
        // new RetryAndFollowUpInterceptor() 重试默认开启，重试 执行21次
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.MINUTES))
                .addInterceptor(new ApplicationLogInterceptor())
                .addInterceptor(new ApplicationMetricsInterceptor())
                .addNetworkInterceptor(new NetworkLogInterceptor())
                .retryOnConnectionFailure(true)
                .build();
        return null;
    }

    public static String get(String baseUrl, Map<String, Object> headerMap, Map<String, Object> paramMap) {
        return null;
    }

    private static Gson getGson() {
        return new GsonBuilder().disableHtmlEscaping().create();
    }

    public static class Resp {
        private volatile Response response = null;
        private volatile IOException exception = null;

        public Resp(Call call) {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Resp.this.exception = e;
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Resp.this.response = response;
                }
            });
        }

        public String getResult() {
            while (response == null && exception == null) {

            }
            if(exception != null) {
                throw new RuntimeException(exception);
            }
            try {
                return response.body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Slf4j
    private static class ApplicationLogInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    }

    @Slf4j
    private static class ApplicationMetricsInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    }

    @Slf4j
    private static class NetworkLogInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response;
        }
    }
}
