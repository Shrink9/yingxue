package com.example.yingxue.common.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频
 * @TableName video
 */
@TableName(value ="video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable {
    public static final String MQ_QUEUE_NAME="play";
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String intro;

    /**
     * up主id
     */
    private Integer uid;

    /**
     * 视频封面链接
     */
    private String cover;

    /**
     * 视频播放链接
     */
    private String link;

    /**
     * 分类id
     */
    private Integer categoryId;

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
