package com.lookstars.video.service;

import com.lookstars.video.domain.Video;
import com.lookstars.video.mapper.VideoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoMapper videoMapper;

    @Test
    public void findAll() {
        List<Video> all = videoService.findAll();
        assertNotNull(all);
        for (Video video : all) {
            System.out.println(video.getTitle());
        }

    }

    @Test
    public void findById() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void save() {
    }
}