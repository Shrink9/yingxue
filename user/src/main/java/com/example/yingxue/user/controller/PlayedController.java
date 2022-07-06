package com.example.yingxue.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.yingxue.common.user.entity.Played;
import com.example.yingxue.common.user.entity.User;
import com.example.yingxue.common.video.entity.Video;
import com.example.yingxue.common.video.vo.VideoVo;
import com.example.yingxue.user.feignclient.VideoClient;
import com.example.yingxue.user.service.PlayedService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class PlayedController{
    private static final Logger log=LoggerFactory.getLogger(PlayedController.class);
    @Autowired
    private UserController userController;
    @Autowired
    private PlayedService playedService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private VideoClient videoClient;
    @GetMapping("/get-play-count-by-video-id")
    public int getPlayCountByVideoId(Integer videoId){
        LambdaQueryWrapper<Played> playedQueryWrapper=new LambdaQueryWrapper<>();
        playedQueryWrapper.eq(Played::getVideoId,videoId);
        return (int)playedService.count(playedQueryWrapper);
    }
    /**
     * 播放视频
     *
     * @param id
     */
    @PutMapping("/user/played/{id}")
    public void playVideo(@PathVariable("id") Integer id,HttpSession session){
        //添加播放次数
        //参数1:队列 参数2:消息(可以是对象)
        rabbitTemplate.convertAndSend(Video.MQ_QUEUE_NAME,id);
        //如果用户已登录则添加用户播放记录
        User loginUserInfo=userController.getLoginUserInfo();
        if(loginUserInfo!=null){
            LambdaQueryWrapper<Played> playedLambdaQueryWrapper=new LambdaQueryWrapper<>();
            playedLambdaQueryWrapper.eq(Played::getUid,loginUserInfo.getId());
            playedLambdaQueryWrapper.eq(Played::getVideoId,id);
            synchronized(session.getId()
                                .intern()){
                Played played=playedService.getOne(playedLambdaQueryWrapper);
                //没有就插入
                if(played==null){
                    played=new Played();
                    played.setUid(loginUserInfo.getId());
                    played.setVideoId(id);
                    played.setCreatedAt(new Date());
                    played.setUpdatedAt(new Date());
                    playedService.save(played);
                    log.debug(loginUserInfo.getId()+"创建了播放记录"+id);
                }
                //已经有了就更新播放时间
                else{
                    Played playedToUpdate=new Played();
                    playedToUpdate.setUpdatedAt(new Date());
                    playedService.update(playedToUpdate,playedLambdaQueryWrapper);
                    log.debug(loginUserInfo.getId()+"更新了播放记录"+id);
                }
            }
        }
    }
    /**
     * 查询播放历史
     */
    @GetMapping("/user/played")
    public List<VideoVo> playVideoHistory(@RequestParam("page") Integer pageNum,@RequestParam("per_page") Integer pageSize){
        //按照时间顺序查询出历史播放视频的id
        User loginUserInfo=userController.getLoginUserInfo();
        LambdaQueryWrapper<Played> playedLambdaQueryWrapper=new LambdaQueryWrapper<>();
        playedLambdaQueryWrapper.eq(Played::getUid,loginUserInfo.getId());
        playedLambdaQueryWrapper.orderByDesc(Played::getUpdatedAt);
        Page<Played> pageCondition=new Page<>(pageNum,pageSize);
        List<Played> playeds=playedService.page(pageCondition,playedLambdaQueryWrapper)
                                          .getRecords();
        if(playeds.size()>0){
            Integer[] ids=new Integer[playeds.size()];
            for(int i=0;i<playeds.size();i++){
                ids[i]=playeds.get(i)
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
