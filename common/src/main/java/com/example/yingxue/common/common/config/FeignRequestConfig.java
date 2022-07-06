package com.example.yingxue.common.common.config;

import feign.RequestInterceptor;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignRequestConfig{
    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate->{
            //拿到当前的所有请求属性
            ServletRequestAttributes attributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            //获取到当前请求
            HttpServletRequest request=attributes.getRequest();
            if(request!=null){
                //同步请求头数据，cookie
                String cookie=request.getHeader("Cookie");
                //给新请求同步老请求的cookie
                requestTemplate.header("Cookie",cookie);
            }
        };
    }
}
