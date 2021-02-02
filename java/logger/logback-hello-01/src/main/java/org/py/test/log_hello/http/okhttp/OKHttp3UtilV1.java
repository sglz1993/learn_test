//package org.py.test.log_hello.http.okhttp;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.util.Map;
//
//public class OKHttp3UtilV1 {
//
//    public static String postJson(String baseUrl, Map<String, Object> bodyParamMap) {
//        return post(baseUrl, null, null, bodyParamMap, true);
//    }
//
//    public static String post(String baseUrl, Map<String, Object> bodyParamMap) {
//        return post(baseUrl, null, null, bodyParamMap, false);
//    }
//
//    public static String get(String baseUrl, Map<String, Object> paramMap) {
//        return get(baseUrl, null, paramMap);
//    }
//
//
//    public static String post(String baseUrl, Map<String, Object> headerMap, Map<String, Object> urlParamMap, Map<String, Object> bodyParamMap, boolean json) {
//
//    }
//
//    public static String get(String baseUrl, Map<String, Object> headerMap, Map<String, Object> paramMap) {
//
//    }
//
//
//    private static Gson getGson() {
//        return new GsonBuilder().disableHtmlEscaping().create();
//    }
//
//}
