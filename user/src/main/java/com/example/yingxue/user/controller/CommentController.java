package com.example.yingxue.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.yingxue.common.user.entity.Comment;
import com.example.yingxue.common.user.entity.User;
import com.example.yingxue.common.user.vo.CommentVo;
import com.example.yingxue.user.service.CommentService;
import com.example.yingxue.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CommentController{
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    /**
     * 分页查询评论列表
     */
    @GetMapping("/get-comments-by-video-id")
    Map<String,Object> getCommentsByVideoId(Integer videoId,Integer pageNum,Integer pageSize){
        HashMap<String,Object> res=new HashMap<>();
        //查找到根评论
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper=new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getVideoId,videoId);
        commentLambdaQueryWrapper.isNull(Comment::getParentId);
        commentLambdaQueryWrapper.orderByDesc(Comment::getCreatedAt);
        Page<Comment> pageCondition=new Page<>(pageNum,pageSize);
        Page<Comment> page=commentService.page(pageCondition,commentLambdaQueryWrapper);
        res.put("total_count",page.getTotal());
        //查出所有评论
        List<Comment> comments=commentService.list();
        List<Comment> commentsToReturn=page.getRecords();
        List<CommentVo> commentVos=new ArrayList<>();
        for(Comment comment: commentsToReturn){
            CommentVo commentVo=new CommentVo();
            BeanUtils.copyProperties(comment,commentVo);
            //封装reviewer
            User user=userService.getById(comment.getUid());
            User reviewer=new User();
            //只传递需要的信息
            reviewer.setId(user.getId());
            reviewer.setName(user.getName());
            reviewer.setAvatar(user.getAvatar());
            commentVo.setReviewer(reviewer);
            //添加子评论
            addChildren(comments,commentVo);
            commentVos.add(commentVo);
        }
        res.put("items",commentVos);
        return res;
    }
    private void addChildren(List<Comment> comments,CommentVo currentCommentVo){
        List<CommentVo> commentVos=new ArrayList<>();
        for(Comment comment: comments){
            if(currentCommentVo.getId()
                               .equals(comment.getParentId())){
                CommentVo commentVo=new CommentVo();
                BeanUtils.copyProperties(comment,commentVo);
                //封装reviewer
                User user=userService.getById(comment.getUid());
                User reviewer=new User();
                //只传递需要的信息
                reviewer.setId(user.getId());
                reviewer.setName(user.getName());
                reviewer.setAvatar(user.getAvatar());
                commentVo.setReviewer(reviewer);
                //添加子评论
                addChildren(comments,commentVo);
                commentVos.add(commentVo);
            }
        }
        //子评论按照时间降序
        commentVos.sort((c1,c2)->{
            if(c1.getCreatedAt().after(c2.getCreatedAt())){
                return -1;
            }
            else{
                if(c1.getCreatedAt().equals(c2.getCreatedAt())){
                    return 0;
                }
                else{
                    return 1;
                }
            }


        });
        currentCommentVo.setChildren(commentVos);
    }
}
