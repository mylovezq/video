package com.lookstars.video.utils;

import com.lookstars.video.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.Assert;

import java.util.Date;

public class JwtUtils {

    public static final String SUBJECT = "lookstars";

    public static final long EXPIRE = 1000 * 60 * 60;//过期时间一个小时

    public static final String APPSECRET = "justlookstars";

    public static String geneJsonWebToken(User user) {
        if (user == null || user.getId() == null || user.getName() == null || user.getHeadImg() == null) {
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("img", user.getHeadImg())
                .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }

    public static Claims checkJWT(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
