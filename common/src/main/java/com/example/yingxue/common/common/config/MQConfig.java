package com.example.yingxue.common.common.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig{
    /**
     * 给容器中放一个用Jackson转换对象和JSON的消息转换器,以便MQ在序列化和反序列化时使用.
     * 如果不放,则默认使用JDK序列化和反序列化方式,因此类必须实现Serializable接口.
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
