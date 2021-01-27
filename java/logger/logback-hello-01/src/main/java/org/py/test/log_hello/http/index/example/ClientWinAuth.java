package org.py.test.log_hello.http.index.example;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.WinHttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 17. 本机Windows Negotiate / NTLM
 * 本示例说明了在Windows OS上运行时如何利用本机Windows Negotiate / NTLM身份验证。
 *
 * <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient-win -->
 * <dependency>
 *     <groupId>org.apache.httpcomponents</groupId>
 *     <artifactId>httpclient-win</artifactId>
 *     <version>4.5.13</version>
 * </dependency>
 *
 * This example demonstrates how to create HttpClient pre-configured
 * with support for integrated Windows authentication.
 */
public class ClientWinAuth {

    public final static void main(String[] args) throws Exception {

        if (!WinHttpClients.isWinAuthAvailable()) {
            System.out.println("Integrated Win auth is not supported!!!");
        }

        CloseableHttpClient httpclient = WinHttpClients.createDefault();
        // There is no need to provide user credentials
        // HttpClient will attempt to access current user security context through
        // Windows platform specific methods via JNI.
        try {
            HttpGet httpget = new HttpGet("http://winhost/");

            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                EntityUtils.consume(response.getEntity());
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}
