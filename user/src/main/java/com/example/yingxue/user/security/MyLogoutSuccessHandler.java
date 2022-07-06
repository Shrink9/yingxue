package com.example.yingxue.user.security;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler{
    private static final Logger log=LoggerFactory.getLogger(MyLogoutSuccessHandler.class);
    //退出成功时回调这个方法
    @Override
    public void onLogoutSuccess(HttpServletRequest request,HttpServletResponse response,Authentication authentication/*认证相关信息(用户数据)*/) throws IOException, ServletException{
        HashMap<String,Object> res=new HashMap<>();
        res.put("msg","退出成功");
        res.put("exitingUser",authentication);
        res.put("status","200");
        response.setContentType("application/json;charset=UTF-8");
        if(authentication!=null){
            log.debug(((User)authentication.getPrincipal()).getUsername()+"已退出");
        }
        response.getWriter().print(new Gson().toJson(res));
    }
}

