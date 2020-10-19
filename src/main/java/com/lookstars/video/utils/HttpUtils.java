package com.lookstars.video.utils;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    private static final Gson gson = new Gson();

    public static Map<String,Object> doGet(String url){
        Map<String,Object> map = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)
                .build();

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String jsonResult = EntityUtils.toString(httpResponse.getEntity());
                String json = new String(jsonResult.getBytes("ISO-8859-1"), "UTF-8");
                System.out.println(json);
                map = gson.fromJson(json, map.getClass());
            }

        }catch (Exception e){
            e.printStackTrace();//会耗性能
        }finally {
               try {
                   httpClient.close();
               }catch (Exception e){
                   e.printStackTrace();
               }
        }

        return map;
    }


    public static Map<String,Object> doPost(String url,String data,int timeOut){
        Map<String,Object> map = new HashMap<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeOut)
                .setConnectionRequestTimeout(timeOut)
                .setSocketTimeout(timeOut)
                .setRedirectsEnabled(true)
                .build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type","text/html; charset=UTF-8");
        if (data != null && data instanceof String){
            StringEntity stringEntity = new StringEntity(data,"UTF-8");
            httpPost.setEntity(stringEntity);
        }
        try{
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String jsonResult = EntityUtils.toString(response.getEntity());
                map = gson.fromJson(jsonResult, map.getClass());
            }
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            try{
                httpClient.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return map;
    }

}
