package com.example.yingxue.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.yingxue.common.user.entity.Following;
import com.example.yingxue.user.service.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowingController{
    @Autowired
    private FollowingService followingService;
    @GetMapping("/get-is-following-by-two-user-id")
    boolean getIsFollowingByTwoUserId(Integer followerId,Integer followeeId){
        LambdaQueryWrapper<Following> followingQueryWrapper=new LambdaQueryWrapper<>();
        followingQueryWrapper.eq(Following::getUid,followerId);
        followingQueryWrapper.eq(Following::getFollowingId,followeeId);
        return followingService.getOne(followingQueryWrapper)!=null;
    }
}
