package com.lookstars.video.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 微信配置类
 */
@Configuration
@PropertySource(value = "classpath:application.properties")
public class WeChatConfig {

    @Value("${wxpay.appid}")
    private  String appId;

    @Value("${wxpay.appsecret}")
    private  String appsecret;

    @Value("${wxpay.redirect_url}")
    private String redirectUrl;


    /**
     * 微信公众号二维码连接
     */

    private final static String OFFICIAL_ACCOUNTS_AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";
    private final static String GET_QRCODE_URL="https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";



    public String getAppId() {
        return appId;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
    public static String getGetQrcodeUrl() {
        return GET_QRCODE_URL;
    }

    public static String getOfficialAccountsAuthorize() {
        return OFFICIAL_ACCOUNTS_AUTHORIZE;
    }
}
