package com.example.yingxue.video.feignclient;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.yingxue.common.user.entity.Like;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="user", contextId="likeClient") //通过Feign访问user集群
public interface LikeClient{
    @GetMapping("/get-is-like-by-user-id-and-video-id")
    boolean getIsLikeByUserIdAndVideoId(@RequestParam("userId") Integer userId,@RequestParam("videoId") Integer videoId);
    @GetMapping("/get-like-count-by-video-id")
    int getLikeCountByVideoId(@RequestParam("videoId") Integer videoId);
}
