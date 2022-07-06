package com.example.yingxue.common.user.vo;

import com.example.yingxue.common.user.entity.Comment;
import com.example.yingxue.common.user.entity.Like;
import com.example.yingxue.common.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo{
    private Integer id;
    private Integer parentId;
    private String content;
    @JsonProperty("created_at")
    private Date createdAt;
    private User reviewer;
    @JsonProperty("sub_comments")
    private List<CommentVo> children;
}
