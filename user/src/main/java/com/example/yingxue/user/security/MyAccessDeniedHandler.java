package com.example.yingxue.user.security;

import com.google.gson.Gson;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request,HttpServletResponse response,AccessDeniedException accessDeniedException) throws IOException, ServletException{
        HashMap<String,String> map=new HashMap<>();
        map.put("msg","没有权限");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter()
                .print(new Gson().toJson(map));
    }
}
