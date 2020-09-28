package com.lookstars.video.service.impl;

import com.lookstars.video.domain.Video;
import com.lookstars.video.mapper.VideoMapper;
import com.lookstars.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll() {
        return videoMapper.findAll();
    }

    @Override
    public Video findById(int id) {
        return videoMapper.findById(id);
    }

    @Override
    public int update(Video video) {
        return videoMapper.update(video);
    }

    @Override
    public int delete(Integer id) {
        return videoMapper.delete(id);
    }

    @Override
    public int save(Video video) {
        int id = videoMapper.save(video);
        System.out.println(id);
        return video.getId();
    }
}
