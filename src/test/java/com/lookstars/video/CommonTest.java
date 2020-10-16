package com.lookstars.video;

import com.lookstars.video.domain.User;
import com.lookstars.video.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

public class CommonTest {

    @Test
    public void testGeneJwt(){
        User user = new User();
        user.setId(9999);
        user.setName("lookstars");
        user.setHeadImg("192.168.1.0");
        String token = JwtUtils.geneJsonWebToken(user);
        System.out.println(token);
    }

    @Test
    public void testCheck(){
//        Claims claims = JwtUtils.checkJWT("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsb29rc3RhcnMiLCJpZCI6OTk5OSwibmFtZSI6Imxvb2tzdGFycyIsImltZyI6IjE5Mi4xNjguMS4wIiwiaWF0IjoxNjAxMjgzOTgyLCJleHAiOjE2MDEyODc1ODJ9.dyXwXL8oswJHGN92x2rsOydeLgL9CYcQyHThv3xQS2k");
//        System.out.println(claims.get("id"));
    }
}
