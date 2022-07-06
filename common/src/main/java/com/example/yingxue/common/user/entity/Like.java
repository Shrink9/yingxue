package com.example.yingxue.common.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 点赞
 * @TableName like
 */
@TableName(value ="`like`")
@Data
public class Like implements Serializable {
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
