package com.example.yingxue.user.security;

import com.example.yingxue.common.user.entity.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //登录成功时回调这个方法
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response,
            Authentication authentication/*认证相关信息(用户数据)*/) throws IOException, ServletException{
        stringRedisTemplate.delete(User.VERIFY_CODE_PREFIX+((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername());
        HashMap<String,Object> res=new HashMap<>();
        //只是为了适应固定的前端,实际没意义.
        res.put("token","Shrink");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(new Gson().toJson(res));
    }
}
