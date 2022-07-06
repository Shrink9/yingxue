package com.example.yingxue.video.feignclient;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.yingxue.common.user.entity.Played;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="user", contextId="playedClient") //通过Feign访问user集群
public interface PlayedClient{
    @GetMapping("/get-play-count-by-video-id")
    int getPlayCountByVideoId(@RequestParam("videoId") Integer videoId);
}
