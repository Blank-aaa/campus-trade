package com.campustrade.entity;

import com.baomidou.mybatisplus.annotation.*;

/**
 * 轮播图实体
 */
@TableName("banner")
public class Banner {

    @TableId(type = IdType.AUTO)
    private Integer id;             // 轮播图ID (Integer)

    private String imageUrl;        // 轮播图图片地址 (String)

    private String linkUrl;         // 点击跳转链接,可为空 (String)

    private Integer sort;           // 排序值,越小越靠前 (Integer)

    @TableLogic
    private Integer deleted;        // 逻辑删除: 0正常 1已删除 (Integer)

    // ===== getter/setter =====
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getLinkUrl() { return linkUrl; }
    public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }
    public Integer getSort() { return sort; }
    public void setSort(Integer sort) { this.sort = sort; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
}
