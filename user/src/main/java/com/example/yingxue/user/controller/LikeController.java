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
public class LikeController{
    @Autowired
    private UserController userController;
    @Autowired
    private LikeService likeService;
    @Autowired
    private DislikeService dislikeService;
    @GetMapping("/get-is-like-by-user-id-and-video-id")
    boolean getIsLikeByUserIdAndVideoId(Integer userId,Integer videoId){
        LambdaQueryWrapper<Like> likeQueryWrapper=new LambdaQueryWrapper<>();
        likeQueryWrapper.eq(Like::getUid,userId);
        likeQueryWrapper.eq(Like::getVideoId,videoId);
        return likeService.getOne(likeQueryWrapper)!=null;
    }
    @GetMapping("/get-like-count-by-video-id")
    public int getLikeCountByVideoId(Integer videoId){
        LambdaQueryWrapper<Like> likeQueryWrapper=new LambdaQueryWrapper<>();
        likeQueryWrapper.eq(Like::getVideoId,videoId);
        return (int)likeService.count(likeQueryWrapper);
    }
    @PutMapping("/user/liked/{videoId}")
    public void like(@PathVariable("videoId") Integer videoId){
        //如果有不喜欢就取消不喜欢
        LambdaQueryWrapper<Dislike> dislikeLambdaQueryWrapper=new LambdaQueryWrapper<>();
        User loginUser=userController.getLoginUserInfo();
        dislikeLambdaQueryWrapper.eq(Dislike::getUid,loginUser.getId());
        dislikeLambdaQueryWrapper.eq(Dislike::getVideoId,videoId);
        dislikeService.remove(dislikeLambdaQueryWrapper);
        //添加点赞
        Like likeToInsert=new Like();
        likeToInsert.setUid(loginUser.getId());
        likeToInsert.setVideoId(videoId);
        likeToInsert.setCreatedAt(new Date());
        likeToInsert.setUpdatedAt(new Date());
        likeService.save(likeToInsert);
    }
    @DeleteMapping("/user/liked/{videoId}")
    public void unLike(@PathVariable("videoId") Integer videoId){
        LambdaQueryWrapper<Like> likeLambdaQueryWrapper=new LambdaQueryWrapper<>();
        User loginUser=userController.getLoginUserInfo();
        likeLambdaQueryWrapper.eq(Like::getUid,loginUser.getId());
        likeLambdaQueryWrapper.eq(Like::getVideoId,videoId);
        likeService.remove(likeLambdaQueryWrapper);
    }
}
