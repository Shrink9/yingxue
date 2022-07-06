package com.example.yingxue.common.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @TableName user
 */
@TableName(value="user")
@Data
public class User implements Serializable{
    public static final String DEFAULT_AVATAR="https://shrink-bucket.oss-cn-hangzhou.aliyuncs.com/main/photo/%E6%9D%BE%E6%9C%AC%E4%B8%80%E9%A6%99.jpg";
    public static final String DEFAULT_INTRO="这个人很懒，还没有自我介绍。";
    public static final String VERIFY_CODE_PREFIX="verifyCode:";
    /**
     *
     */
    @TableId(type=IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 头像链接
     */
    private String avatar;
    /**
     * 简介
     */
    private String intro;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 是否绑定手机号
     */
    private Integer phoneLinked;
    /**
     * 微信openid
     */
    private String openid;
    /**
     * 是否绑定微信
     */
    private Integer wechatLinked;
    /**
     * 关注数
     */
    private Integer followingCount;
    /**
     * 粉丝数
     */
    private Integer followersCount;
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
    @TableField(exist=false)
    private List<Role> roles;
    @TableField(exist=false)
    private static final long serialVersionUID=1L;
}
