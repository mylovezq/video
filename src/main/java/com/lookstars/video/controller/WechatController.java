package com.lookstars.video.controller;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lookstars.video.config.WeChatConfig;
import com.lookstars.video.domain.JsonData;
import com.lookstars.video.domain.User;
import com.lookstars.video.service.UserService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
;

import com.google.gson.Gson;

@RestController
@RequestMapping("wechat")
public class WechatController {

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private UserService userService;
    private static final Gson gson = new Gson();

    /***
     * httpClient-Get请求
     * @param url 请求地址
     * @return
     * @throws Exception
     */
    public static Map<String, Object> httpClientGet(String url) throws Exception {
        HttpClient client = new HttpClient();
        client.getParams().setContentCharset("UTF-8");
        GetMethod httpGet = new GetMethod(url);
        try {
            client.executeMethod(httpGet);
            String response = httpGet.getResponseBodyAsString();
            Map<String, Object> map = gson.fromJson(response, Map.class);
            return map;
        } catch (Exception e) {
            throw e;
        } finally {
            httpGet.releaseConnection();
        }
    }

    /***
     * httpClient-Post请求
     * @param url 请求地址
     * @param params post参数
     * @return
     * @throws Exception
     */
    public static Map<String, Object> httpClientPost(String url, String params) throws Exception {
        HttpClient client = new HttpClient();
        client.getParams().setContentCharset("UTF-8");
        PostMethod httpPost = new PostMethod(url);
        try {
            RequestEntity requestEntity = new ByteArrayRequestEntity(params.getBytes("utf-8"));
            httpPost.setRequestEntity(requestEntity);
            client.executeMethod(httpPost);
            String response = httpPost.getResponseBodyAsString();
            Map<String, Object> map = gson.fromJson(response, Map.class);
            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            httpPost.releaseConnection();
        }
    }


    // 生成带参数的二维码，自动登录网站
    @RequestMapping("getLoginUrl")
    public JsonData wechatMpLogin(@RequestParam String access_page) throws Exception {
        String encodeRedirectUrl = URLEncoder.encode(weChatConfig.getRedirectUrl(), "GBK");
        String createQrcodeUrl = String.format(weChatConfig.getGetQrcodeUrl(), weChatConfig.getAppId(), encodeRedirectUrl, access_page);
        return JsonData.buildSuccess(createQrcodeUrl);
    }

    @RequestMapping("getWechatAuth")
    public JsonData getWechatAuth(@RequestParam String access_page) throws Exception {
        String encodeRedirectUrl = URLEncoder.encode(weChatConfig.getRedirectUrl(), "GBK");
        String createQrcodeUrl = String.format(weChatConfig.getOfficialAccountsAuthorize(), weChatConfig.getAppId(), encodeRedirectUrl, access_page);
        return JsonData.buildSuccess(createQrcodeUrl);
    }

    @GetMapping("userCallback")
    public void callback(@RequestParam String code, String state, HttpServletResponse httpServletResponse) throws Exception {
        User user = userService.saveWeChatUser(code);
        if (user != null) {
            //生产jwt
        }
    }

}