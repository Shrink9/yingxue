package com.example.yingxue.common.category.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

/**
 * 分类
 *
 * @TableName category
 */
@TableName(value="category")
@Data
public class Category implements Serializable{
    /**
     *
     */
    @TableId(type=IdType.AUTO)
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父级分类id
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
    @TableField(exist=false)
    private static final long serialVersionUID=1L;
    @TableField(exist=false)
    private ArrayList<Category> children;
}
