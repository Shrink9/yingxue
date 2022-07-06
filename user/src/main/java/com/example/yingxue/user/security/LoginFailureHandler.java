package com.example.yingxue.user.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler{
    //登录失败时回调这个方法
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,HttpServletResponse response,AuthenticationException exception/*异常信息*/) throws IOException{
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
