package com.example.yingxue.video.feignclient;

import com.example.yingxue.common.user.entity.Comment;
import com.example.yingxue.common.user.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

//映射类：
@FeignClient(value="user",contextId="userClient") //通过Feign访问user集群
public interface UserClient{
    @GetMapping("/user/{userId}")
    User queryUserById(@PathVariable("userId") Integer userId);
    @GetMapping("/user/video/{videoId}")
    Map<String,Object> getVideoDetailByVideoId(@PathVariable("videoId") Integer videoId);
    @PostMapping("/user/comment")
    void userComment(@RequestBody Comment comment);
}
