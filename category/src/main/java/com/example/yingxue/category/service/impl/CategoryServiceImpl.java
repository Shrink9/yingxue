package com.example.yingxue.category.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yingxue.common.category.entity.Category;
import com.example.yingxue.category.service.CategoryService;
import com.example.yingxue.category.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService{
}




