package com.campustrade.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campustrade.common.PageDTO;
import com.campustrade.entity.Order;
import com.campustrade.entity.Product;
import com.campustrade.entity.User;
import com.campustrade.mapper.OrderMapper;
import com.campustrade.mapper.ProductMapper;
import com.campustrade.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 我的板块 — 用户信息、商品发布管理、订单
 */
@Service
public class UserService {

    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

    public UserService(UserMapper userMapper, ProductMapper productMapper, OrderMapper orderMapper) {
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.orderMapper = orderMapper;
    }

    /** 获取用户信息 */
    public User info(Long userId) {
        return userMapper.selectById(userId);
    }

    /** 修改用户信息,只更新传了值的字段 */
    public void updateInfo(Long userId, String nickname, String avatar, String phone, String school) {
        User u = userMapper.selectById(userId);
        if (u == null) return;
        if (nickname != null) u.setNickname(nickname);
        if (avatar != null) u.setAvatar(avatar);
        if (phone != null) u.setPhone(phone);
        if (school != null) u.setSchool(school);
        userMapper.updateById(u);
    }

    /** 发布商品,默认状态为"在售",浏览量0 */
    public void publish(Long userId, String title, String description, BigDecimal price,
                        BigDecimal originalPrice, String images, Integer categoryId) {
        Product p = new Product();
        p.setUserId(userId);
        p.setTitle(title);
        p.setDescription(description);
        p.setPrice(price);
        p.setOriginalPrice(originalPrice);
        p.setImages(images);
        p.setCategoryId(categoryId);
        p.setStatus(1);         // 1=在售
        p.setViewCount(0);
        productMapper.insert(p);
    }

    /** 我发布的商品分页列表(含已售和下架的) */
    public PageDTO<Product> myPublished(Long userId, int page, int size) {
        IPage<Product> p = productMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getUserId, userId)
                        .orderByDesc(Product::getCreateTime));
        return PageDTO.of(p.getTotal(), p.getCurrent(), p.getSize(), p.getRecords());
    }

    /** 修改发布的商品,校验userId防止越权 */
    public void updateProduct(Long userId, Long productId, String title, String description,
                              BigDecimal price, BigDecimal originalPrice, String images, Integer categoryId) {
        Product p = productMapper.selectById(productId);
        if (p == null || !p.getUserId().equals(userId)) return;
        if (title != null) p.setTitle(title);
        if (description != null) p.setDescription(description);
        if (price != null) p.setPrice(price);
        if (originalPrice != null) p.setOriginalPrice(originalPrice);
        if (images != null) p.setImages(images);
        if (categoryId != null) p.setCategoryId(categoryId);
        productMapper.updateById(p);
    }

    /** 下架商品,状态改为3,校验userId防止越权 */
    public void offShelf(Long userId, Long productId) {
        Product p = productMapper.selectById(productId);
        if (p == null || !p.getUserId().equals(userId)) return;
        p.setStatus(3);     // 3=已下架
        productMapper.updateById(p);
    }

    /** 我的订单(买家视角),可选按状态筛选 */
    public PageDTO<Order> myOrders(Long userId, Integer status, int page, int size) {
        LambdaQueryWrapper<Order> qw = new LambdaQueryWrapper<Order>()
                .eq(Order::getBuyerId, userId);
        if (status != null) qw.eq(Order::getStatus, status);
        qw.orderByDesc(Order::getCreateTime);
        IPage<Order> p = orderMapper.selectPage(new Page<>(page, size), qw);
        return PageDTO.of(p.getTotal(), p.getCurrent(), p.getSize(), p.getRecords());
    }

    /**
     * 创建订单:
     * 1. 校验商品存在且在售
     * 2. 校验不能买自己的商品
     * 3. 生成订单编号(CT + 年月日时分秒 + 6位随机)
     * 4. 保存订单快照(标题/图片/单价)
     * 5. 商品状态改为已售
     */
    public String createOrder(Long buyerId, Long productId, int quantity, String remark) {
        Product p = productMapper.selectById(productId);
        if (p == null || p.getStatus() != 1) throw new RuntimeException("商品已下架或不存在");
        if (p.getUserId().equals(buyerId)) throw new RuntimeException("不能购买自己的商品");

        Order order = new Order();
        order.setOrderNo("CT" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + UUID.randomUUID().toString().substring(0, 6));
        order.setBuyerId(buyerId);
        order.setSellerId(p.getUserId());
        order.setProductId(productId);
        order.setProductTitle(p.getTitle());                    // 快照:标题
        order.setProductImage(firstImage(p.getImages()));       // 快照:首图
        order.setPrice(p.getPrice());                           // 快照:单价
        order.setQuantity(quantity);
        order.setTotalAmount(p.getPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setStatus(1);     // 1=待发货
        order.setRemark(remark);
        orderMapper.insert(order);

        p.setStatus(2);         // 商品改为已售
        productMapper.updateById(p);

        return order.getOrderNo();
    }

    /** 取逗号分隔图片列表的首张 */
    private String firstImage(String images) {
        if (images == null || images.isEmpty()) return "";
        int i = images.indexOf(",");
        return i > 0 ? images.substring(0, i) : images;
    }
}
