package com.lookstars.video.controller.admin;

import com.lookstars.video.domain.Video;
import com.lookstars.video.mapper.VideoMapper;
import com.lookstars.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/video")
public class VideoAdminController {

    @Autowired
    private VideoService videoService;

    @PutMapping("updateById")
    public Object update(@RequestBody Video video){
        return videoService.update(video);
    }

    @PostMapping("save")
    public Object save(@RequestBody Video video){
        return videoService.save(video);
    }
}
