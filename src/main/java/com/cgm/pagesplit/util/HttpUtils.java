package com.cgm.pagesplit.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtils {
    public static String doGet(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return doRequest(httpGet);

    }

    public static String doPost(String url, String requestBody) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(requestBody, "UTF-8");
        httpPost.setEntity(entity);
        return doRequest(httpPost);
    }

    private static String doRequest(HttpRequestBase requestBase) throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(requestBase)) {
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
