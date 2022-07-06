package com.example.yingxue.util.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="user") //通过Feign访问user集群
public interface LikeClient{
    @GetMapping("/get-like-count-by-video-id")
    int getLikeCountByVideoId(@RequestParam("videoId") Integer videoId);
}
