package com.example.yingxue.video.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//映射类：
@FeignClient(value="user", contextId="favoriteClient") //通过Feign访问user集群
public interface FavoriteClient{
    @GetMapping("/get-is-favorite-by-user-id-and-video-id")
    boolean getIsFavoriteByUserIdAndVideoId(@RequestParam("userId") Integer userId,@RequestParam("videoId") Integer videoId);
}
