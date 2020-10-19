package com.lookstars.video.controller;

import com.lookstars.video.domain.JsonData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/api/v1/order")
public class OrderController {

      @PostMapping("add")
      public JsonData saveOrder(){
          return JsonData.buildSuccess("下单成功！");
      }


}
