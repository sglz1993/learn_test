package org.py.test.log_hello.http.httpClient.baiyi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * 说明：
 *   只是初始的Http请求工具类
 *
 * 设置说明：
 *   1. RequestBuilder 和 构建后的各种HttpXXX 对象添加Header是一致的
 *
 * 优化方向：
 *   1. 异常重试处理
 *   2. Client可以不用每次都创建，通过提供统一的Client、线程池配置等
 *   3. 通过添加请求拦截器、响应拦截器、在请求响应前后进行统一处理，比如：日志记录或
 *   4. 可以通过 HttpContext 来传递一些数据
 *   5. 对ssl的支持
 *   6. 可以考虑是否使用缓存
 *   7. 重定向的支持
 *
 * 问题：
 *   1. FileEntity与FileBody
 *   2. 缓存使用的注意点
 *   3. RequestBuilder的各种请求类型
 *   4. form-data和x-www-form-urlencoded的本质区别
 *          https://blog.csdn.net/u013827143/article/details/86222486
 *   5. 对文件、流、字节数组的支持
 *   6. 对json的支持
 */
@SuppressWarnings("ALL")
@Slf4j
public class HttpClientUtilV1 {

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
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URIBuilder uriBuilder = new URIBuilder(baseUrl);
            if(urlParamMap != null) {
                for(Map.Entry<String, Object> entry : urlParamMap.entrySet()) {
                    Object value = entry.getValue();
                    if(value != null) {
                        uriBuilder.addParameter(entry.getKey(), getGson().toJson(entry.getValue()));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            if(headerMap != null) {
                for(Map.Entry<String, Object> entry: headerMap.entrySet()) {
                    Object value = entry.getValue();
                    if(value != null) {
                        httpPost.addHeader(entry.getKey(), getGson().toJson(value));
                    }
                }
            }
            if (MapUtils.isNotEmpty(bodyParamMap)) {
                if (json) {
                    httpPost.setEntity(new StringEntity(getGson().toJson(bodyParamMap)));
                } else {
                    MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    for (Map.Entry<String, Object> entry : bodyParamMap.entrySet()) {
                        Object value = entry.getValue();
                        if(value == null) {
                            continue;
                        }
                        if (value instanceof File) {
                            builder.addBinaryBody(entry.getKey(), (File) value);
                        } else if (value instanceof InputStream) {
                            builder.addBinaryBody(entry.getKey(), (InputStream) value);
                        } else if (value instanceof byte[]) {
                            builder.addBinaryBody(entry.getKey(), (byte[]) value);
                        } else {
                            builder.addTextBody(entry.getKey(), getGson().toJson(value));
                        }
                    }
                    httpPost.setEntity(builder.build());
                }
            }
            return httpclient.execute(httpPost, getStringResponseHandler());
        }catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String baseUrl, Map<String, Object> headerMap, Map<String, Object> paramMap) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URIBuilder uriBuilder = new URIBuilder(baseUrl);
            if(paramMap != null) {
                for(Map.Entry<String, Object> entry : paramMap.entrySet()) {
                    Object value = entry.getValue();
                    if(value != null) {
                        uriBuilder.addParameter(entry.getKey(), getGson().toJson(entry.getValue()));
                    }
                }
            }
            HttpGet httpget = new HttpGet(uriBuilder.build());
            if(headerMap != null) {
                for(Map.Entry<String, Object> entry: headerMap.entrySet()) {
                    Object value = entry.getValue();
                    if(value != null) {
                        httpget.addHeader(entry.getKey(), getGson().toJson(value));
                    }
                }
            }
            ResponseHandler<String> responseHandler = getStringResponseHandler();
            return httpclient.execute(httpget, responseHandler);
        }catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static Gson getGson() {
        return new GsonBuilder().disableHtmlEscaping().create();
    }

    private static ResponseHandler<String> getStringResponseHandler () {
        ResponseHandler<String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };
        return responseHandler;
    }
}
