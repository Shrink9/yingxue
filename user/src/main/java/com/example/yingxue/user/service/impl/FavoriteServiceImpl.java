package com.example.yingxue.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yingxue.common.user.entity.Favorite;
import com.example.yingxue.user.service.FavoriteService;
import com.example.yingxue.user.mapper.FavoriteMapper;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper,Favorite> implements FavoriteService{
}




