package com.lookstars.video.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lookstars.video.domain.Video;
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
    public Object delById(@RequestParam(value = "video_id",required = true)int videoId){
        return videoService.delete(videoId);
    }


}
