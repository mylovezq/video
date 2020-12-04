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

    @Value("${open.appid}")
    private  String openAppId;

    @Value("${open.appsecret}")
    private  String openAppSecret;

    @Value("${open.redirect_url}")
    private String openRedirectUrl;


    /**
     * 商户号id
     */
    @Value("${mch.id}")
    private String mchId;

    /**
     * 支付key
     */
    @Value("${mch.api.key}")
    private String mchKey;

    /**
     * 支付回调地址
     */
     @Value("${official.redirect_url}")
     private String payCallbackUrl;

    /**
     * 微信公众号id
     */
    @Value("${official.appid}")
    private String officialAppId;


    /**
     * 微信公众号二维码连接
     */

    private final static String OPEN_QRCODE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";

    private final static String OPEN_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private final static String OPEN_ACCESS_USER_INFO_URL="https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * 统一下单
     */
    private final static String UNIFIED_ORDER_URL="https://api.mch.weixin.qq.com/pay/unifiedorder";

    public String getOpenAppId() {
        return openAppId;
    }

    public void setOpenAppId(String openAppId) {
        this.openAppId = openAppId;
    }

    public String getOpenAppSecret() {
        return openAppSecret;
    }

    public void setOpenAppSecret(String openAppSecret) {
        this.openAppSecret = openAppSecret;
    }

    public String getOpenRedirectUrl() {
        return openRedirectUrl;
    }

    public void setOpenRedirectUrl(String openRedirectUrl) {
        this.openRedirectUrl = openRedirectUrl;
    }

    public static String getOpenQrcodeUrl() {
        return OPEN_QRCODE_URL;
    }

    public static String getOpenAccessTokenUrl() {
        return OPEN_ACCESS_TOKEN_URL;
    }

    public static String getOpenAccessUserInfoUrl() {
        return OPEN_ACCESS_USER_INFO_URL;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMchKey() {
        return mchKey;
    }

    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    public String getPayCallbackUrl() {
        return payCallbackUrl;
    }

    public void setPayCallbackUrl(String payCallbackUrl) {
        this.payCallbackUrl = payCallbackUrl;
    }

    public String getOfficialAppId() {
        return officialAppId;
    }

    public void setOfficialAppId(String officialAppId) {
        this.officialAppId = officialAppId;
    }

    public static String getUnifiedOrderUrl() {
        return UNIFIED_ORDER_URL;
    }
}
