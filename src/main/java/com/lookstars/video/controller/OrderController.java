package com.lookstars.video.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lookstars.video.domain.JsonData;
import com.lookstars.video.dto.VideoOrderDto;
import com.lookstars.video.service.VideoOrderService;
import com.lookstars.video.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/need/order")
public class OrderController {

    @Autowired
    private VideoOrderService videoOrderService;

      @GetMapping("add")
      public void saveOrder(@RequestParam int videoId, HttpServletRequest request, HttpServletResponse response){

          String ipAddr = IpUtils.getIpAddr(request);
          int userId = (Integer)request.getAttribute("user_id");

          VideoOrderDto videoOrderDto = new VideoOrderDto();
          videoOrderDto.setUserId(userId);
          videoOrderDto.setVideoId(videoId);
          videoOrderDto.setIp(ipAddr);
          String codeUrl = null;
          try {
              codeUrl = videoOrderService.save(videoOrderDto);
              if (codeUrl==null){
                  throw new NullPointerException("codeUrl为空");
              }
              //生成二维码
              Map<EncodeHintType,Object> hints = new HashMap<>();

              hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

              hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");

              BitMatrix bitMatrix =new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE,500,500,hints);

              OutputStream outputStream = response.getOutputStream();

              MatrixToImageWriter.writeToStream(bitMatrix,"png",outputStream);
          } catch (Exception e) {
              e.printStackTrace();
          }


      }




}
