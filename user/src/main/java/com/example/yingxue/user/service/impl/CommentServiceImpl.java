package com.example.yingxue.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.yingxue.common.user.entity.Comment;
import com.example.yingxue.user.service.CommentService;
import com.example.yingxue.user.mapper.CommentMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment> implements CommentService{
}




