package com.example.yingxue.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yingxue.common.user.entity.Comment;
import com.example.yingxue.common.user.entity.User;
import com.example.yingxue.common.video.vo.VideoVo;
import com.example.yingxue.user.feignclient.VideoClient;
import com.example.yingxue.user.service.CommentService;
import com.example.yingxue.user.service.UserService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController{
    @Autowired
    private VideoClient videoClient;
    @Autowired
    private Gson gson;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    private static final Logger log=LoggerFactory.getLogger(UserController.class);
    @PostMapping("/captchas")
    public void sendVerifyCode(@RequestBody HashMap<String,String> phoneMap){
        stringRedisTemplate.delete(User.VERIFY_CODE_PREFIX+phoneMap.get("phone"));
        stringRedisTemplate.opsForValue()
                           .set(User.VERIFY_CODE_PREFIX+phoneMap.get("phone"),"1234",5,TimeUnit.MINUTES);
        log.debug("验证码为1234");
    }
    @GetMapping("/user")
    public User queryUserInfo(){
        Authentication authentication=SecurityContextHolder.getContext()
                                                           .getAuthentication();
        org.springframework.security.core.userdetails.User loginUser=(org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("phone",loginUser.getUsername());
        User user=userService.getOne(userQueryWrapper);
        return user;
    }
    @PatchMapping("/user")
    public User updateUserInfo(@RequestBody Map<String,String> data){
        Authentication authentication=SecurityContextHolder.getContext()
                                                           .getAuthentication();
        //查到当前用户
        org.springframework.security.core.userdetails.User loginUser=(org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("phone",loginUser.getUsername());
        User user=userService.getOne(userQueryWrapper);
        User userToUpdate=gson.fromJson(gson.toJson(data),User.class);
        String newPhone=data.get("phone");
        if(StringUtils.isNotEmpty(newPhone)){
            String correctVerifyCode=stringRedisTemplate.opsForValue()
                                                        .get(User.VERIFY_CODE_PREFIX+newPhone);
            if(!correctVerifyCode.equals(data.get("captcha"))){
                userToUpdate.setPhone(null);
            }
            else{
                QueryWrapper<User> tempQueryWrapper=new QueryWrapper<>();
                tempQueryWrapper.eq("phone",newPhone);
                User one=userService.getOne(tempQueryWrapper);
                //如果改手机号已被绑定则不能再绑定
                if(one!=null){
                    userToUpdate.setPhone(null);
                }
                else{
                    //修改手机号后需要退出账号
                    SecurityContextHolder.clearContext();
                }
            }
        }
        userToUpdate.setId(user.getId());
        //更新数据
        userService.updateById(userToUpdate);
        return userService.getById(user.getId());
    }
    //@PreAuthorize("hasAnyRole('up')")
    @PostMapping("/user/videos")
    public Map<String,Object> uploadVideo(MultipartFile file,String title,String intro,@RequestParam("category_id") Integer categoryId){
        //调用video服务
        return videoClient.uploadVideo(file,title,intro,categoryId,getLoginUserInfo().getId());
    }
    @GetMapping("/user/{userId}")
    public User queryUserById(@PathVariable("userId") Integer userId){
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("id",userId);
        User user=userService.getOne(userQueryWrapper);
        return user;
    }
    @PreAuthorize("hasAnyRole('up')")
    @GetMapping("/user-videos")
    public List<VideoVo> getVideos(){
        //调用video服务
        return videoClient.getVideoByUserId(getLoginUserInfo().getId());
    }
    @GetMapping("/user/video/{videoId}")
    public Map<String,Object> getVideoDetailByVideoId(@PathVariable("videoId") Integer videoId){
        User loginUser=getLoginUserInfo();
        //未登录
        if(loginUser==null){
            return videoClient.getVideoDetailById(videoId);
        }
        //已登录
        else{
            return videoClient.getVideoDetailById(videoId,getLoginUserInfo().getId());
        }
    }
    public User getLoginUserInfo(){
        //查登录用户的id
        Authentication authentication=SecurityContextHolder.getContext()
                                                           .getAuthentication();
        org.springframework.security.core.userdetails.User loginUser;
        try{
            loginUser=(org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        }
        //未登录
        catch(Exception e){
            return null;
        }
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("phone",loginUser.getUsername());
        User user=userService.getOne(userQueryWrapper);
        return user;
    }
    @PostMapping("/user/comment")
    public void userComment(@RequestBody Comment comment){
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(new Date());
        comment.setUid(getLoginUserInfo().getId());
        commentService.save(comment);
    }
}
