package com.example.yingxue.video.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.yingxue.common.video.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public interface VideoService extends IService<Video>{
    Page<Video> generalPage(Integer pageNum,Integer pageSize,Video videoCondition);
    List<Video> generalList(Video videoCondition);
}
