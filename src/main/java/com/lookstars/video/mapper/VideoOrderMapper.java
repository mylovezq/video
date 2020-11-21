package com.lookstars.video.mapper;

import com.lookstars.video.domain.VideoOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VideoOrderMapper {

    @Insert("INSERT INTO `xdclass`.`video_order`(`openid`, `out_trade_no`, `state`, `create_time`, `notify_time`, `total_fee`, `nickname`, `head_img`, `video_id`, `video_title`, `video_img`, `user_id`, `ip`, `del`) VALUES " +
            "(#{openid} ,#{outTradeNo} ,#{state} ,#{createTime} ,#{notifyTime} ,#{totalFee} ,#{nickname} ,#{headImg} ,#{videoId} ,#{videoTitle} ,#{videoImg} ,#{userId} ,#{ip} ,#{del})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insert(VideoOrder videoOrder);


    @Select("select * from video_order where id=#{order_id} and del=0")
    VideoOrder findById(int order_id);


    @Select("select * from video_order where out_trade_no=#{outTradeNo} and del=0")
    VideoOrder findByOutTrade(String outTradeNo);


    @Update("update video_order set del=1 where id=#{id} and user_id =#{userId}")
    int del(int userId,int id);

    @Select("select * from video_order where user_id =#{userId} ")
    List<VideoOrder> findMyOrderList(int userId);

    @Update("update video_order set state =#{state },notify_time=#{notifyTime},openid=#{openid}" +
            "where out_trade_no=#{outTradeNo} and state=0 and del=0")
    int updateVideoOrderByOutTradeNo(VideoOrder videoOrder);
}
