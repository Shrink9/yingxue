package com.example.yingxue.video.controller;

import com.aliyun.oss.OSS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.yingxue.common.user.entity.Comment;
import com.example.yingxue.common.user.entity.Played;
import com.example.yingxue.common.user.entity.User;
import com.example.yingxue.common.video.entity.Video;
import com.example.yingxue.video.feignclient.*;
import com.example.yingxue.video.service.VideoService;
import com.example.yingxue.common.video.vo.VideoVo;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RefreshScope
@RestController
public class VideoController{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${spring.cloud.alicloud.oss.bucket}")
    String bucket;
    @Value("${spring.cloud.alicloud.oss.endpoint}")
    String endpoint;
    @Value("${spring.cloud.alicloud.oss.keyPrefix}")
    String keyPrefix;
    @Value("${spring.cloud.alicloud.oss.host}")
    String host;
    String coverLinkParam="?x-oss-process=video/snapshot,t_1000,f_jpg,w_0,h_0,m_fast,ar_auto";
    @Autowired
    private Gson gson;
    @Autowired
    private VideoService videoService;
    @Autowired
    private OSS ossClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private FavoriteClient favoriteClient;
    @Autowired
    private LikeClient likeClient;
    @Autowired
    private DislikeClient dislikeClient;
    @Autowired
    private FollowingClient followingClient;
    @Autowired
    private CommentClient commentClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/upload-video")
    public VideoVo uploadVideo(MultipartFile file,String title,String intro,Integer categoryId,Integer userId) throws Exception{
        //上传文件
        //参数:
        //    bucketName: bucket名字
        //    key: 存放到bucket中文件的路径
        //    file: 要上传的本地文件，当前端传过来文件时，这个参数换成流即可。
        //根据OSS文件路径计算规则(http://bucket.endpoint/key)可知上传后文件路径为
        //http://shrink-bucket.oss-cn-hangzhou.aliyuncs.com/main/test/Java后端开发学习路线-高清版.jpg
        //文件名加UUID防止重复
        String finalFileName=FilenameUtils.getBaseName(file.getOriginalFilename())+"-"+UUID.randomUUID()
                                                                                           .toString()
                                                                                           .substring(0,5)+"."+FilenameUtils.getExtension(file.getOriginalFilename());
        //存到OSS
        ossClient.putObject(bucket,"main/video/"+finalFileName,file.getInputStream());
        String videoLink=host+"/"+keyPrefix+finalFileName;
        String coverLink=videoLink+coverLinkParam;
        Video videoToInsert=new Video(null,title,intro,userId,coverLink,videoLink,categoryId,new Date(),new Date(),null);
        videoService.save(videoToInsert);
        VideoVo reultVideoVo=constructVideoVoByVideo(videoToInsert);
        //把数据通过mq给search让其存放到es中
        //参数1:队列 参数2:消息(可以是对象)
        rabbitTemplate.convertAndSend(VideoVo.MQ_QUEUE_NAME,reultVideoVo);
        //返回数据
        return reultVideoVo;
    }
    @GetMapping("/recommends")
    public ArrayList<VideoVo> page(@RequestParam("page") Integer pageNum,@RequestParam("per_page") Integer pageSize){
        List<Video> videos=videoService.generalPage(pageNum,pageSize,null)
                                       .getRecords();
        int size=videos.size();
        ArrayList<VideoVo> resultVideoVos=new ArrayList<>();
        for(int i=0;i<size;i++){
            resultVideoVos.add(constructVideoVoByVideo(videos.get(i)));
        }
        return resultVideoVos;
    }
    public VideoVo constructVideoVoByVideo(Video video){
        VideoVo videoVo=new VideoVo();
        BeanUtils.copyProperties(video,videoVo);
        //种类名
        String categoryName=categoryClient.getCategoryById(video.getCategoryId())
                                          .getName();
        videoVo.setCategory(categoryName);
        //up主
        String uploader=userClient.queryUserById(video.getUid())
                                  .getName();
        videoVo.setUploader(uploader);
        //点赞数量
        int likeCount=likeClient.getLikeCountByVideoId(video.getId());
        videoVo.setLikes(likeCount);
        return videoVo;
    }
    @GetMapping("/get-video-by-user-id")
    public List<VideoVo> getVideoByUserId(Integer userId){
        Video videoCondition=new Video();
        videoCondition.setUid(userId);
        List<Video> videos=videoService.generalList(videoCondition);
        ArrayList<VideoVo> videoVos=new ArrayList<>();
        for(Video video: videos){
            videoVos.add(constructVideoVoByVideo(video));
        }
        return videoVos;
    }
    @GetMapping("/videos")
    public List<VideoVo> getVideoByCategory(@RequestParam("page") Integer pageNum,@RequestParam("per_page") Integer pageSize,@RequestParam("category") Integer categoryId){
        Video videoCondition=new Video();
        videoCondition.setCategoryId(categoryId);
        Page<Video> videoPage=videoService.generalPage(pageNum,pageSize,videoCondition);
        List<Video> videos=videoPage.getRecords();
        ArrayList<VideoVo> videoVos=new ArrayList<>();
        for(Video video: videos){
            videoVos.add(constructVideoVoByVideo(video));
        }
        return videoVos;
    }
    /**
     * 登录情况下查看视频详情
     */
    @GetMapping("/videos/login/{id}")
    public Map<String,Object> getVideoDetailById(@PathVariable("id") Integer id,Integer userId){
        Map<String,Object> res=getVideoDetailById(id);
        res.put("liked",likeClient.getIsLikeByUserIdAndVideoId(userId,id));
        res.put("disliked",dislikeClient.getIsDislikeByUserIdAndVideoId(userId,id));
        res.put("favorite",favoriteClient.getIsFavoriteByUserIdAndVideoId(userId,id));
        Map<String,Object> up=(Map<String,Object>)res.get("uploader");
        up.put("followed",followingClient.getIsFollowingByTwoUserId(userId,(Integer)up.get("id")));
        return res;
    }
    /**
     * 未登录情况下查看视频详情
     */
    @GetMapping("/videos/no-login/{id}")
    public Map<String,Object> getVideoDetailById(@PathVariable("id") Integer id){
        Map<String,Object> res=new HashMap<>();
        Video video=videoService.getById(id);
        res.put("id",id);
        res.put("title",video.getTitle());
        res.put("link",video.getLink());
        res.put("created_at",video.getCreatedAt());
        res.put("updated_at",video.getUpdatedAt());
        res.put("category",categoryClient.getCategoryById(video.getCategoryId())
                                         .getName());
        User up=userClient.queryUserById(video.getUid());
        HashMap<String,Object> upMap=new HashMap<>();
        upMap.put("id",up.getId());
        upMap.put("name",up.getName());
        upMap.put("avatar",up.getAvatar());
        res.put("uploader",upMap);
        //播放次数
        int playCount;
        String playCountStr=(String)stringRedisTemplate.opsForHash()
                                                       .get(Played.PLAY_COUNT_KEY,String.valueOf(id));
        if(playCountStr==null){
            playCount=0;
        }
        else{
            playCount=NumberUtils.toInt(playCountStr);
        }
        res.put("plays_count",playCount);
        res.put("likes_count",likeClient.getLikeCountByVideoId(id));
        return res;
    }
    @GetMapping("/videos/{videoId}")
    public Map<String,Object> forwardGetVideoDetail(@PathVariable("videoId") Integer videoId){
        return userClient.getVideoDetailByVideoId(videoId);
    }
    @GetMapping("/get-videos-by-ids")
    public List<VideoVo> getVideosByIds(Integer[] ids){
        LambdaQueryWrapper<Video> videoLambdaQueryWrapper=new LambdaQueryWrapper<>();
        videoLambdaQueryWrapper.in(Video::getId,ids);
        List<Video> videos=videoService.list(videoLambdaQueryWrapper);
        List<VideoVo> videoVos=new ArrayList<>();
        for(Video video: videos){
            videoVos.add(constructVideoVoByVideo(video));
        }
        return videoVos;
    }
    @GetMapping("/videos/{videoId}/comments")
    public Map<String,Object> forwardGetCommentsByVideoId(@PathVariable("videoId") Integer videoId,@RequestParam("page") Integer pageNum,@RequestParam("per_page") Integer pageSize){
        return commentClient.getCommentsByVideoId(videoId,pageNum,pageSize);
    }
    @PostMapping("/videos/{videoId}/comments")
    public void userComment(@RequestBody Comment comment,@PathVariable("videoId") Integer videoId){
        comment.setVideoId(videoId);
        userClient.userComment(comment);
    }
}
