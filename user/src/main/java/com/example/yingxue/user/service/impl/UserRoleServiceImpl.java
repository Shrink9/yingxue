package com.example.yingxue.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yingxue.common.user.entity.UserRole;
import com.example.yingxue.user.service.UserRoleService;
import com.example.yingxue.user.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper,UserRole> implements UserRoleService{
}




