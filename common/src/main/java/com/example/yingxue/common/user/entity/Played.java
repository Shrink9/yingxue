package com.example.yingxue.common.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 播放历史
 * @TableName played
 */
@TableName(value ="played")
@Data
public class Played implements Serializable {
    public static final String PLAY_COUNT_KEY="playCount";

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
