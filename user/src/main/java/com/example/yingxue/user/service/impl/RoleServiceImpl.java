package com.example.yingxue.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yingxue.common.user.entity.Role;
import com.example.yingxue.user.service.RoleService;
import com.example.yingxue.user.mapper.RoleMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService{
}




