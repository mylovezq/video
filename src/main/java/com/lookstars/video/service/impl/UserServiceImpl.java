package com.lookstars.video.service.impl;

import com.lookstars.video.config.WeChatConfig;
import com.lookstars.video.domain.User;
import com.lookstars.video.mapper.UserMapper;
import com.lookstars.video.service.UserService;
import com.lookstars.video.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserMapper userMapper;
    @Override
    public User saveWeChatUser(String code) {
        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(), weChatConfig.getOpenAppId(), weChatConfig.getOpenAppSecret(), code);
        Map<String, Object> baseMap = HttpUtils.doGet(accessTokenUrl);
        if (CollectionUtils.isEmpty(baseMap)) return null;
        //获取accessToken
        String accessToken = (String)baseMap.get("access_token");
        String openid = (String)baseMap.get("openid");

        User byOpenid = userMapper.findByOpenid(openid);
         if (byOpenid != null){
              return byOpenid;
         }
        //通过accessToken 获取用户信息
        String userInfoUrl = String.format(WeChatConfig.getOpenAccessUserInfoUrl(),accessToken, openid);
        Map<String, Object> userInfoMap = HttpUtils.doGet(userInfoUrl);
        if (CollectionUtils.isEmpty(userInfoMap)) return null;

        User user = new User();
        user.setOpenid(openid);
        user.setName((String)userInfoMap.get("nickname"));
        user.setHeadImg((String)userInfoMap.get("headimgurl"));
        user.setCity((String)userInfoMap.get("city"));
        user.setProvince((String)userInfoMap.get("province"));
        user.setCountry((String)userInfoMap.get("country"));
        user.setSex((Double) userInfoMap.get("sex") == 1  ? "男":"女");
        user.setCreateTime(new Date());
        StringBuilder finalAddress = new StringBuilder(user.getCity()).append("||").append(user.getProvince()).append("||").append(user.getCountry());
        user.setFinalAddress(finalAddress.toString());
        userMapper.save(user);
        return user;
    }
}
