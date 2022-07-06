package com.example.yingxue.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yingxue.common.user.entity.Following;
import com.example.yingxue.user.service.FollowingService;
import com.example.yingxue.user.mapper.FollowingMapper;
import org.springframework.stereotype.Service;

@Service
public class FollowingServiceImpl extends ServiceImpl<FollowingMapper,Following> implements FollowingService{
}




