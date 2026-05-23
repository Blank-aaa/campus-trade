package com.campustrade.entity;

import com.baomidou.mybatisplus.annotation.*;

/**
 * 商品分类实体
 */
@TableName("category")
public class Category {

    @TableId(type = IdType.AUTO)
    private Integer id;         // 分类ID (Integer)

    private String name;        // 分类名称 (String, 如"数码电子"、"书籍教材")

    private String icon;        // 分类图标 (String, emoji字符)

    private Integer sort;       // 排序值,越小越靠前 (Integer)

    @TableLogic
    private Integer deleted;    // 逻辑删除: 0正常 1已删除 (Integer)

    // ===== getter/setter =====
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
}
