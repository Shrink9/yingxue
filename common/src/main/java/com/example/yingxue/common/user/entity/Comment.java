package com.example.yingxue.common.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 视频id
     */
    private Integer videoId;

    /**
     * 内容
     */
    private String content;

    /**
     * 父评论id
     */
    private Integer parentId;

    /**
     *
     */
    private Date createdAt;

    /**
     *
     */
    private Date updatedAt;

    /**
     *
     */
    private Date deletedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
