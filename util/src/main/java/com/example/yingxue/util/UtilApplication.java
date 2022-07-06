package com.example.yingxue.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UtilApplication{
    public static void main(String[] args){
        SpringApplication.run(UtilApplication.class,args);
    }
}
