package com.lookstars.video.service.impl;

import com.lookstars.video.domain.Video;
import com.lookstars.video.mapper.VideoMapper;
import com.lookstars.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
@Service
@Transactional(rollbackFor = Exception.class)
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
     Assert.notNull(findById(id),"该条记录不存在！");
        return videoMapper.delete(id);
    }

    @Override
    public int save(Video video) {
        int id = videoMapper.save(video);
        System.out.println(id);
        return video.getId();
    }
}
