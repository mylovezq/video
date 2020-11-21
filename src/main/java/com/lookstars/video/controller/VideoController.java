package com.lookstars.video.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lookstars.video.domain.JsonData;
import com.lookstars.video.domain.Video;
import com.lookstars.video.domain.VideoOrder;
import com.lookstars.video.mapper.VideoMapper;
import com.lookstars.video.mapper.VideoOrderMapper;
import com.lookstars.video.service.VideoService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Service;
import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    VideoOrderMapper videoOrderMapper;
    /**
     * 分页接口
     * @param page 默认第一页
     * @param size 每页条数，默认10
     * @return
     */
    @GetMapping("page")
    public Object pageVideo(@RequestParam(value = "page",defaultValue = "1")int page,
                            @RequestParam(value = "size",defaultValue = "10")int size){
        PageHelper.startPage(page,size);
        List<Video> all = videoService.findAll();
        PageInfo<Video> pageInfo = new PageInfo<>(all);
        return pageInfo;
    }

    @GetMapping("findById")
    public Object findById(@RequestParam(value = "video_id",required = true) int videoId){
        return videoService.findById(videoId);
    }

    @DeleteMapping("delById")
    public JsonData delById(@RequestParam(value = "video_id",required = true)int videoId){

        try{
            videoService.delete(videoId);
            return JsonData.buildSuccess(videoId);
        }catch (Exception e){
            return JsonData.buildError(e.getMessage());
        }


    }


}
