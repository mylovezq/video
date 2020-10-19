package com.lookstars.video.mapper;

import com.lookstars.video.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where id =#{userId}")
    User findById(int userId);

    @Select("select * from user where openid =#{openid}")
    User findByOpenid(String openid);

    @Insert("INSERT INTO `xdclass`.`user`( `openid`, `name`, `head_img`, `phone`, `sign`, `sex`, `city`, `province`, `country`, `finalAddress`, `create_time`) VALUES " +
            "(#{openid}, #{name}, #{headImg}, #{phone}, #{sign}, #{sex}, #{city}, #{province}, #{country}, #{finalAddress}, #{createTime});")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn ="id")
    int save(User user);
}

