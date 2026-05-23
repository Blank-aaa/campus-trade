package com.campustrade.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体
 */
@TableName("product")
public class Product {

    @TableId(type = IdType.AUTO)
    private Long id;                    // 商品ID (Long)

    private String title;               // 商品标题 (String)

    private String description;         // 商品描述 (String)

    private BigDecimal price;           // 售价 (BigDecimal, 如2999.00)

    private BigDecimal originalPrice;   // 原价/划线价 (BigDecimal, 可为null, 如5999.00)

    private String images;              // 商品图片,多张用逗号分隔 (String, 如"a.jpg,b.jpg,c.jpg")

    private Integer categoryId;         // 所属分类ID,对应Category.id (Integer)

    private Long userId;                // 发布者用户ID (Long)

    private Integer status;             // 状态: 1在售  2已售  3已下架 (Integer)

    private Integer viewCount;          // 浏览量 (Integer)

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;       // 发布时间,自动填充 (String,ISO格式)

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;       // 更新时间,自动填充 (String,ISO格式)

    @TableLogic
    private Integer deleted;                // 逻辑删除标记: 0正常 1已删除 (Integer)

    // ===== getter/setter =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(BigDecimal originalPrice) { this.originalPrice = originalPrice; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
}
