package com.example.yingxue.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yingxue.common.user.entity.User;
import com.example.yingxue.user.service.UserService;
import com.example.yingxue.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{
}




