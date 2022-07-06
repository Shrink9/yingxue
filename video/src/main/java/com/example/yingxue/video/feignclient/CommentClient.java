package com.example.yingxue.video.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value="user") //通过Feign访问user集群
public interface CommentClient{
    @GetMapping("/get-comments-by-video-id")
    Map<String,Object> getCommentsByVideoId(@RequestParam("videoId") Integer videoId,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize);
}
