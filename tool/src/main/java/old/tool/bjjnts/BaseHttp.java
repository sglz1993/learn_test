package old.tool.bjjnts;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

/**
 * @author: pengyue.du
 * @time: 2020/7/9 5:32 下午
 */
public class BaseHttp {

    public String get(String url, Map<String, String> headerMap) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        if (headerMap != null) {
            for (Map.Entry<String, String> entity : headerMap.entrySet()) {
                builder.addHeader(entity.getKey(), String.valueOf(entity.getValue()));
            }
        }
        Request request = builder
                .url(url)
                .build();

        try {
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
