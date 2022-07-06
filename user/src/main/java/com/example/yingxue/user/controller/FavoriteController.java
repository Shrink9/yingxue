package com.example.yingxue.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.yingxue.common.user.entity.Favorite;
import com.example.yingxue.common.user.entity.User;
import com.example.yingxue.common.video.vo.VideoVo;
import com.example.yingxue.user.feignclient.VideoClient;
import com.example.yingxue.user.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class FavoriteController{
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private UserController userController;
    @Autowired
    private VideoClient videoClient;
    @GetMapping("/get-is-favorite-by-user-id-and-video-id")
    boolean getIsFavoriteByUserIdAndVideoId(Integer userId,Integer videoId){
        LambdaQueryWrapper<Favorite> favoriteQueryWrapper=new LambdaQueryWrapper<>();
        favoriteQueryWrapper.eq(Favorite::getUid,userId);
        favoriteQueryWrapper.eq(Favorite::getVideoId,videoId);
        return favoriteService.getOne(favoriteQueryWrapper)!=null;
    }
    @PutMapping("/user/favorites/{videoId}")
    public void favorite(@PathVariable("videoId") Integer videoId){
        User loginUser=userController.getLoginUserInfo();
        //添加收藏
        Favorite favoriteToInsert=new Favorite();
        favoriteToInsert.setUid(loginUser.getId());
        favoriteToInsert.setVideoId(videoId);
        favoriteToInsert.setCreatedAt(new Date());
        favoriteToInsert.setUpdatedAt(new Date());
        favoriteService.save(favoriteToInsert);
    }
    @DeleteMapping("/user/favorites/{videoId}")
    public void unFavorite(@PathVariable("videoId") Integer videoId){
        LambdaQueryWrapper<Favorite> favoriteLambdaQueryWrapper=new LambdaQueryWrapper<>();
        User loginUser=userController.getLoginUserInfo();
        favoriteLambdaQueryWrapper.eq(Favorite::getUid,loginUser.getId());
        favoriteLambdaQueryWrapper.eq(Favorite::getVideoId,videoId);
        favoriteService.remove(favoriteLambdaQueryWrapper);
    }
    @GetMapping("/user/favorites")
    public List<VideoVo> getFavorites(@RequestParam("page") Integer pageNum,@RequestParam("per_page") Integer pageSize){
        //按照时间顺序查询出收藏视频的id
        User loginUserInfo=userController.getLoginUserInfo();
        LambdaQueryWrapper<Favorite> favoriteLambdaQueryWrapper=new LambdaQueryWrapper<>();
        favoriteLambdaQueryWrapper.eq(Favorite::getUid,loginUserInfo.getId());
        favoriteLambdaQueryWrapper.orderByDesc(Favorite::getUpdatedAt);
        Page<Favorite> pageCondition=new Page<>(pageNum,pageSize);
        List<Favorite> favorites=favoriteService.page(pageCondition,favoriteLambdaQueryWrapper)
                                          .getRecords();
        if(favorites.size()>0){
            Integer[] ids=new Integer[favorites.size()];
            for(int i=0;i<favorites.size();i++){
                ids[i]=favorites.get(i)
                                .getVideoId();
            }
            //查询出视频信息
            List<VideoVo> videoVos=videoClient.getVideosByIds(ids);
            //因为视频顺序是任意的,所以把视频按照上面的ids进行排序.
            videoVos.sort((v1,v2)->{
                int size=ids.length;
                int v1Index=-1;
                int v2Index=-1;
                for(int i=0;i<size;i++){
                    if(v1Index==-1&&v1.getId()
                                      .equals(ids[i])){
                        v1Index=i;
                    }
                    else{
                        if(v2Index==-1&&v2.getId()
                                          .equals(ids[i])){
                            v2Index=i;
                        }
                    }
                    if(v1Index!=-1&&v2Index!=-1){
                        break;
                    }
                }
                return v1Index-v2Index;
            });
            return videoVos;
        }
        else{
            return null;
        }
    }
}
