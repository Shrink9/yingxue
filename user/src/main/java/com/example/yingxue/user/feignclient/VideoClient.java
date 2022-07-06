package com.example.yingxue.user.feignclient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yingxue.common.video.entity.Video;
import com.example.yingxue.common.video.vo.VideoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//映射类：
@FeignClient("video") //通过Feign访问video集群
public interface VideoClient{
    //指定这个方法对应的url以及参数类型和返回类型
    @PostMapping(value="/upload-video",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    Map<String,Object> uploadVideo(@RequestPart("file") MultipartFile file,@RequestParam("title") String title,
            @RequestParam("intro") String intro,@RequestParam("categoryId") Integer categoryId,@RequestParam("userId") Integer userId);
    @GetMapping("/get-video-by-user-id")
    List<VideoVo> getVideoByUserId(@RequestParam("userId") Integer userId);
    @GetMapping("/videos/login/{id}")
    Map<String,Object> getVideoDetailById(@PathVariable("id") Integer id,@RequestParam("userId") Integer userId);
    @GetMapping("/videos/no-login/{id}")
    Map<String,Object> getVideoDetailById(@PathVariable("id") Integer id);
    @GetMapping("/get-videos-by-ids")
    List<VideoVo> getVideosByIds(@RequestParam("ids") Integer[] ids);
}
