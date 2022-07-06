package com.example.yingxue.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yingxue.common.video.entity.Video;
import com.example.yingxue.video.service.VideoService;
import com.example.yingxue.video.mapper.VideoMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper,Video> implements VideoService{

    @Autowired
    private VideoMapper videoMapper;
    /**
     * 通用的分页查询
     */
    @Override
    public Page<Video> generalPage(Integer pageNum,Integer pageSize,Video videoCondition){
        LambdaQueryWrapper<Video> queryWrapper=new LambdaQueryWrapper<>();
        if(videoCondition!=null){
            queryWrapper.eq(ObjectUtils.isNotEmpty(videoCondition.getCategoryId()),Video::getCategoryId,videoCondition.getCategoryId());
            queryWrapper.eq(ObjectUtils.isNotEmpty(videoCondition.getCover()),Video::getCover,videoCondition.getCover());
            queryWrapper.eq(ObjectUtils.isNotEmpty(videoCondition.getCreatedAt()),Video::getCreatedAt,videoCondition.getCreatedAt());
            queryWrapper.eq(ObjectUtils.isNotEmpty(videoCondition.getDeletedAt()),Video::getDeletedAt,videoCondition.getDeletedAt());
            queryWrapper.eq(ObjectUtils.isNotEmpty(videoCondition.getId()),Video::getId,videoCondition.getId());
            queryWrapper.eq(ObjectUtils.isNotEmpty(videoCondition.getIntro()),Video::getIntro,videoCondition.getIntro());
            queryWrapper.eq(ObjectUtils.isNotEmpty(videoCondition.getLink()),Video::getLink,videoCondition.getLink());
            queryWrapper.eq(ObjectUtils.isNotEmpty(videoCondition.getTitle()),Video::getTitle,videoCondition.getTitle());
            queryWrapper.eq(ObjectUtils.isNotEmpty(videoCondition.getUid()),Video::getUid,videoCondition.getUid());
            queryWrapper.eq(ObjectUtils.isNotEmpty(videoCondition.getUpdatedAt()),Video::getUpdatedAt,videoCondition.getUpdatedAt());
        }
        Page<Video> videoPage;
        //不分页
        if(pageNum==null||pageSize==null){
            List<Video> videos=videoMapper.selectList(queryWrapper);
            videoPage=new Page<>();
            videoPage.setRecords(videos);
        }
        //分页
        else{
            Page<Video> videoPageCondition=new Page<>(pageNum,pageSize);
            videoPage=videoMapper.selectPage(videoPageCondition,queryWrapper);
        }
        return videoPage;
    }
    /**
     * 通用的列表查询
     */
    @Override
    public List<Video> generalList(Video videoCondition){
        Page<Video> videoPage=generalPage(null,null,videoCondition);
        return videoPage.getRecords();
    }
}




