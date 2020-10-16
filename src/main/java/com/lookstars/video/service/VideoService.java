package com.lookstars.video.service;

import com.lookstars.video.domain.Video;


import java.util.List;

public interface VideoService {

    List<Video> findAll();

    Video findById(int id);

    int update(Video video);

    int delete(Integer id);

    int save(Video video);

}
