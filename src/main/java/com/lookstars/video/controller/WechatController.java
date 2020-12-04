package com.lookstars.video.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lookstars.video.config.WeChatConfig;
import com.lookstars.video.domain.JsonData;
import com.lookstars.video.domain.User;
import com.lookstars.video.domain.VideoOrder;
import com.lookstars.video.service.UserService;
import com.lookstars.video.service.VideoOrderService;
import com.lookstars.video.utils.JwtUtils;
import com.lookstars.video.utils.WXPayUtil;
import io.jsonwebtoken.Claims;
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

    @Autowired
    private VideoOrderService videoOrderService;
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
        String encodeRedirectUrl = URLEncoder.encode(weChatConfig.getOpenRedirectUrl(), "GBK");
        String createQrcodeUrl = String.format(WeChatConfig.getOpenQrcodeUrl(), weChatConfig.getOpenAppId(), encodeRedirectUrl, access_page);
        return JsonData.buildSuccess(createQrcodeUrl);
    }


    @GetMapping("userCallback")
    public void callback(@RequestParam String code, String state, HttpServletResponse httpServletResponse) throws Exception {
        User user = userService.saveWeChatUser(code);
        if (user != null) {
            //生产jwt
            String token = JwtUtils.geneJsonWebToken(user);
            String url = state+"?token="+token+"&head_img="+user.getHeadImg()+"&name="+URLEncoder.encode(user.getName(),"utf-8");
            httpServletResponse.sendRedirect(url);

        }

    }

    @PostMapping("orderCallback")
    public void orderCallBack(HttpServletRequest request,HttpServletResponse response){
        try {
            InputStream inputStream = request.getInputStream();
            //
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = null;
            StringBuffer stringBuffer = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null){
                 stringBuffer.append(line);
            }

            bufferedReader.close();

            inputStream.close();


            Map<String, String> callbackMap = WXPayUtil.xmlToMap(stringBuffer.toString());

            SortedMap<String, String> sortedMap = WXPayUtil.getSortedMap(callbackMap);

            //判断签名
            if (WXPayUtil.isCorrectSign(sortedMap,weChatConfig.getMchKey())){
                if ("SUCCESS".equals(sortedMap.get("result_code"))){

                    String outTradeNo = sortedMap.get("out_trade_no");
                    VideoOrder byOutTradeNo = videoOrderService.findByOutTradeNo(outTradeNo);
                    if (byOutTradeNo.getState()==0) { //判断逻辑据具体看业务场景
                        VideoOrder videoOrder = new VideoOrder();
                        videoOrder.setOpenid(sortedMap.get("openid"));
                        videoOrder.setOutTradeNo(outTradeNo);
                        videoOrder.setNotifyTime(new Date());
                        videoOrder.setState(1);
                        int rows = videoOrderService.updateVideoOderByOutTradeNo(videoOrder);
                        if (rows == 1){
                            response.setContentType("text/xml");
                            response.getWriter().print("success");
                            return;
                        }

                    }
                }
            }
            //都处理失败
            response.setContentType("text/xml");
            response.getWriter().print("fail");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}