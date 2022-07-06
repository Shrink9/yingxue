package com.example.yingxue.video.feignclient;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.yingxue.common.user.entity.Following;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="user", contextId="followingClient") //通过Feign访问user集群
public interface FollowingClient{
    @GetMapping("/get-is-following-by-two-user-id")
    boolean getIsFollowingByTwoUserId(@RequestParam("followerId") Integer followerId,
            @RequestParam("followeeId") Integer followeeId);
}
