package com.lookstars.video.controller;

import com.lookstars.video.config.WeChatConfig;
import com.lookstars.video.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class test {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private VideoMapper videoMapper;

    @GetMapping("test")
    public String show(){
        System.out.println("测试！");
        return "hell world,mylovezqd 夜空中最的啊啊亮的星！可以很强";
    }

    
    @GetMapping(value = "findAll")
    public Object findAll(){

        return videoMapper.findAll();
    }
}
