package com.campustrade.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

/**
 * 购物车项实体
 */
@TableName("cart_item")
public class CartItem {

    @TableId(type = IdType.AUTO)
    private Long id;                    // 购物车项ID (Long)

    private Long userId;                // 用户ID (Long)

    private Long productId;             // 商品ID (Long)

    private Integer quantity;           // 购买数量 (Integer)

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;       // 加入时间,自动填充 (String,ISO格式)

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;       // 更新时间,自动填充 (String,ISO格式)

    // ===== getter/setter =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}
