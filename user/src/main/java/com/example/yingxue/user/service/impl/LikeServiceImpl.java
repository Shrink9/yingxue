package com.example.yingxue.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yingxue.common.user.entity.Like;
import com.example.yingxue.user.service.LikeService;
import com.example.yingxue.user.mapper.LikeMapper;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper,Like> implements LikeService{
}




