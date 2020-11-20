package com.lookstars.video;

import com.google.gson.Gson;
import com.lookstars.video.domain.JsonData;
import com.lookstars.video.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    private static final Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("token");
        if (token == null) {
            token = request.getParameter("token");
        }
        if (token != null) {
            Claims claims = JwtUtils.checkJWT(token);
            if (claims != null) {
                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");

                request.setAttribute("user_id", id);
                request.setAttribute("name", name);

                return true;
            }
        }
            sendJsonMessage(response, JsonData.buildError("请登录！"));
            return false;
        }

        public static void sendJsonMessage (HttpServletResponse httpServletResponse, Object obj) throws IOException {
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.print(gson.toJson(obj));
            writer.close();
            httpServletResponse.flushBuffer();
        }

    }
