package org.py.test.log_hello.http.okhttp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.Assert;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
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
    // new RetryAndFollowUpInterceptor() 重试默认开启，重试 执行21次
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(5, 1, TimeUnit.MINUTES))
            .addInterceptor(new ApplicationLogInterceptor())
            .addInterceptor(new ApplicationMetricsInterceptor())
            .addNetworkInterceptor(new NetworkLogInterceptor())
            .retryOnConnectionFailure(true)
            .build();

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
        Request.Builder builder = new Request.Builder();
        try {
            return HTTP_CLIENT.newCall(builder.build()).execute().body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            RequestTracker requestTracker = new RequestTracker(chain);
            log.info("request:{}", getGson().toJson(requestTracker));
            Request request = chain.request();
            Response response = chain.proceed(request);
            requestTracker.record(response);
            log.info("response:{}", getGson().toJson(requestTracker));
            return response;
        }
    }

    @Slf4j
    private static class ApplicationMetricsInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            RequestTracker requestTracker = new RequestTracker(chain);
            log.info("request:{}", getGson().toJson(requestTracker));
            Request request = chain.request();
            Response response = chain.proceed(request);
            requestTracker.record(response);
            log.info("response:{}", getGson().toJson(requestTracker));
            return response;
        }
    }

    @Slf4j
    private static class NetworkLogInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            RequestTracker requestTracker = new RequestTracker(chain);
            log.info("request:{}", getGson().toJson(requestTracker));
            Request request = chain.request();
            Response response = chain.proceed(request);
            requestTracker.record(response);
            log.info("response:{}", getGson().toJson(requestTracker));
            return response;
        }
    }

    @Data
    private static class RequestTracker {

        private String url;
        private Map<String, List<String>> headerMap;
        private Object body;
        private ZonedDateTime start;
        private Long latency;

        private int responseStatus;
        private String responseBody;
        private Map<String, List<String>> responseHeaderMap;

        public RequestTracker(Interceptor.Chain chain) {
            Assert.notNull(chain, "chain is null");
            Request request = chain.request();
            url = request.url().toString();
            headerMap = request.headers().toMultimap();
            MediaType mediaType = request.body().contentType();

            start = ZonedDateTime.now();
        }

        public Long record(Response response) {
            responseStatus = response.code();
            try {
                responseBody = response.body().string();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            responseHeaderMap = response.headers().toMultimap();
            if(start != null) {
                latency = ZonedDateTime.now().toInstant().toEpochMilli() - start.toInstant().toEpochMilli();
            }
            return latency;
        }

    }

}
