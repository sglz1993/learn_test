package org.py.test.log_hello.http.index.example;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 4. 中止方法
 * 此示例演示如何在正常完成之前中止HTTP请求。
 *
 * This example demonstrates how to abort an HTTP method before its normal completion.
 */
public class _4_ClientAbortMethod {

    public final static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://httpbin.org/get");

            System.out.println("Executing request " + httpget.getURI());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                // Do not feel like reading the response body
                // Call abort on the request object
                httpget.abort();
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}