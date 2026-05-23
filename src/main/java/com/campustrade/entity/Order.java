package com.campustrade.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体
 */
@TableName("`order`")
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;                    // 订单主键ID (Long)

    private String orderNo;             // 订单编号 (String, 如"CT20260520120000a1b2c3")

    private Long buyerId;               // 买家用户ID (Long)

    private Long sellerId;              // 卖家用户ID (Long)

    private Long productId;             // 商品ID (Long)

    private String productTitle;        // 商品标题快照,下单时的标题 (String)

    private String productImage;        // 商品图片快照,下单时的首图 (String)

    private BigDecimal price;           // 商品单价快照 (BigDecimal)

    private Integer quantity;           // 购买数量 (Integer)

    private BigDecimal totalAmount;     // 订单总金额 = price × quantity (BigDecimal)

    private Integer status;             // 订单状态: 1待发货  2待收货  3已完成  4已取消 (Integer)

    private String remark;              // 买家备注 (String)

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;       // 下单时间,自动填充 (String,ISO格式)

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;       // 更新时间,自动填充 (String,ISO格式)

    // ===== getter/setter =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }
    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductTitle() { return productTitle; }
    public void setProductTitle(String productTitle) { this.productTitle = productTitle; }
    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
}
