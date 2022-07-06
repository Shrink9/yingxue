package com.example.yingxue.user.security;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class JsonLoginFilter extends UsernamePasswordAuthenticationFilter{
    private Map<String,String> usernameAndPassword=null;
    @Override
    protected String obtainUsername(HttpServletRequest request){
        try{
            //将流数据转化成map
            usernameAndPassword=new Gson().fromJson(new InputStreamReader(request.getInputStream()),Map.class);
            return usernameAndPassword.get(getUsernameParameter());
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected String obtainPassword(HttpServletRequest request){
        String password=usernameAndPassword.get(getPasswordParameter());
        return password;
    }
}
