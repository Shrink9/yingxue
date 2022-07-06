package com.example.yingxue.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.yingxue.common.user.entity.Dislike;
import com.example.yingxue.common.user.entity.Like;
import com.example.yingxue.common.user.entity.User;
import com.example.yingxue.user.service.DislikeService;
import com.example.yingxue.user.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class DislikeController{
    @Autowired
    private DislikeService disislikeService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private UserController userController;
    @GetMapping("/get-is-dislike-by-user-id-and-video-id")
    boolean getIsDislikeByUserIdAndVideoId(Integer userId,Integer videoId){
        LambdaQueryWrapper<Dislike> disislikeQueryWrapper=new LambdaQueryWrapper<>();
        disislikeQueryWrapper.eq(Dislike::getUid,userId);
        disislikeQueryWrapper.eq(Dislike::getVideoId,videoId);
        return disislikeService.getOne(disislikeQueryWrapper)!=null;
    }
    @PutMapping("/user/disliked/{videoId}")
    public void dislike(@PathVariable("videoId") Integer videoId){
        //如果有点赞就取消点赞
        LambdaQueryWrapper<Like> likeLambdaQueryWrapper=new LambdaQueryWrapper<>();
        User loginUser=userController.getLoginUserInfo();
        likeLambdaQueryWrapper.eq(Like::getUid,loginUser.getId());
        likeLambdaQueryWrapper.eq(Like::getVideoId,videoId);
        likeService.remove(likeLambdaQueryWrapper);
        //添加不喜欢
        Dislike dislikeToInsert=new Dislike();
        dislikeToInsert.setUid(loginUser.getId());
        dislikeToInsert.setVideoId(videoId);
        dislikeToInsert.setCreatedAt(new Date());
        dislikeToInsert.setUpdatedAt(new Date());
        disislikeService.save(dislikeToInsert);
    }
    @DeleteMapping("/user/disliked/{videoId}")
    public void unDislike(@PathVariable("videoId") Integer videoId){
        LambdaQueryWrapper<Dislike> dislikeLambdaQueryWrapper=new LambdaQueryWrapper<>();
        User loginUser=userController.getLoginUserInfo();
        dislikeLambdaQueryWrapper.eq(Dislike::getUid,loginUser.getId());
        dislikeLambdaQueryWrapper.eq(Dislike::getVideoId,videoId);
        disislikeService.remove(dislikeLambdaQueryWrapper);
    }
}
