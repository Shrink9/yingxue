package com.example.yingxue.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yingxue.common.user.entity.Played;
import com.example.yingxue.user.service.PlayedService;
import com.example.yingxue.user.mapper.PlayedMapper;
import org.springframework.stereotype.Service;

@Service
public class PlayedServiceImpl extends ServiceImpl<PlayedMapper,Played> implements PlayedService{
}




