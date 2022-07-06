package com.example.yingxue.common.common.config;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 给容器中加入自定义的RedisCacheConfiguration(和默认的区别在于把value的序列化方式从jdk序列化换成了json)
 * <br/><br/>
 * 启用缓存
 */
@EnableCaching
@Configuration
public class RedisCacheConfig{
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){
        RedisCacheConfiguration redisCacheConfiguration=RedisCacheConfiguration.defaultCacheConfig();
        CacheProperties.Redis redisCacheProperties=cacheProperties.getRedis();
        //设置key的序列化方式,序列化为字符串.
        redisCacheConfiguration=redisCacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        //设置value的序列化方式,序列化为json,jackson.date-format的设置对序列化到缓存中不生效.
        redisCacheConfiguration=redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        //从配置文件进行相关设置
        if(redisCacheProperties.getTimeToLive()!=null){
            redisCacheConfiguration=redisCacheConfiguration.entryTtl(redisCacheProperties.getTimeToLive());
        }
        if(redisCacheProperties.getKeyPrefix()!=null){
            redisCacheConfiguration=redisCacheConfiguration.prefixKeysWith(redisCacheProperties.getKeyPrefix());
        }
        if(!redisCacheProperties.isCacheNullValues()){
            redisCacheConfiguration=redisCacheConfiguration.disableCachingNullValues();
        }
        if(!redisCacheProperties.isUseKeyPrefix()){
            redisCacheConfiguration=redisCacheConfiguration.disableKeyPrefix();
        }
        return redisCacheConfiguration;
    }
}
