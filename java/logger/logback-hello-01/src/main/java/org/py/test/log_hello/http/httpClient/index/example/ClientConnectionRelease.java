package org.py.test.log_hello.http.httpClient.index.example;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

/**
 * 2. 手动连接释放
 * 此示例演示了在手动处理HTTP响应的情况下如何确保将基础HTTP连接释放回连接管理器。
 * This example demonstrates the recommended way of using API to make sure
 * the underlying connection gets released back to the connection manager.
 */
public class ClientConnectionRelease {

    public final static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://httpbin.org/get");

            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());

                // Get hold of the response entity
                HttpEntity entity = response.getEntity();

                // If the response does not enclose an entity, there is no need
                // to bother about connection release
                if (entity != null) {
                    InputStream inStream = entity.getContent();
                    try {
                        inStream.read();
                        // do something useful with the response
                    } catch (IOException ex) {
                        // In case of an IOException the connection will be released
                        // back to the connection manager automatically
                        throw ex;
                    } finally {
                        // Closing the input stream will trigger connection release
                        inStream.close();
                    }
                }
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}