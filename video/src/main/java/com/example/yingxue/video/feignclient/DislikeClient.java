package com.example.yingxue.video.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="user", contextId="dislikeClient") //通过Feign访问user集群
public interface DislikeClient{
    @GetMapping("/get-is-dislike-by-user-id-and-video-id")
    boolean getIsDislikeByUserIdAndVideoId(@RequestParam("userId") Integer userId,@RequestParam("videoId") Integer videoId);
}
