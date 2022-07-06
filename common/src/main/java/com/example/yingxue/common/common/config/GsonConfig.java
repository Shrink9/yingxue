package com.example.yingxue.common.common.config;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig{
    @ConditionalOnMissingBean(Gson.class)
    @Bean
    Gson gson(){
        return new Gson();
    }
}
