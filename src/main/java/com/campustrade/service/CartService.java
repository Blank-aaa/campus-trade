package com.campustrade.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.campustrade.entity.CartItem;
import com.campustrade.entity.Product;
import com.campustrade.mapper.CartItemMapper;
import com.campustrade.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 购物车板块 — 增删改查,自动关联商品详情
 */
@Service
public class CartService {

    private final CartItemMapper cartItemMapper;
    private final ProductMapper productMapper;

    public CartService(CartItemMapper cartItemMapper, ProductMapper productMapper) {
        this.cartItemMapper = cartItemMapper;
        this.productMapper = productMapper;
    }

    /**
     * 购物车列表,每条记录自动拼接对应商品的标题/价格/首图
     * 商品已下架或已售出的不会出现在列表中
     */
    public List<Map<String, Object>> list(Long userId) {
        List<CartItem> items = cartItemMapper.selectList(
                new LambdaQueryWrapper<CartItem>().eq(CartItem::getUserId, userId));
        List<Map<String, Object>> result = new ArrayList<>();
        for (CartItem item : items) {
            Product p = productMapper.selectById(item.getProductId());
            if (p == null || p.getStatus() != 1) continue;  // 跳过已下架/已售商品
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("cartId", item.getId());
            m.put("productId", p.getId());
            m.put("title", p.getTitle());
            m.put("price", p.getPrice());
            m.put("image", firstImage(p.getImages()));
            m.put("quantity", item.getQuantity());
            result.add(m);
        }
        return result;
    }

    /**
     * 加入购物车,同一商品已存在则数量累加,否则新增记录
     */
    public void add(Long userId, Long productId, int quantity) {
        CartItem exist = cartItemMapper.selectOne(
                new LambdaQueryWrapper<CartItem>()
                        .eq(CartItem::getUserId, userId)
                        .eq(CartItem::getProductId, productId));
        if (exist != null) {
            exist.setQuantity(exist.getQuantity() + quantity);     // 已有则累加数量
            cartItemMapper.updateById(exist);
        } else {
            CartItem item = new CartItem();
            item.setUserId(userId);
            item.setProductId(productId);
            item.setQuantity(quantity);
            cartItemMapper.insert(item);
        }
    }

    /**
     * 修改数量,若数量<=0则自动删除该项
     */
    public void updateQuantity(Long cartId, Long userId, int quantity) {
        CartItem item = cartItemMapper.selectById(cartId);
        if (item != null && item.getUserId().equals(userId)) {
            if (quantity <= 0) {
                cartItemMapper.deleteById(cartId);
            } else {
                item.setQuantity(quantity);
                cartItemMapper.updateById(item);
            }
        }
    }

    /**
     * 删除购物车项,校验userId防止越权
     */
    public void delete(Long cartId, Long userId) {
        cartItemMapper.delete(
                new LambdaQueryWrapper<CartItem>()
                        .eq(CartItem::getId, cartId)
                        .eq(CartItem::getUserId, userId));
    }

    /** 取逗号分隔图片列表的首张 */
    private String firstImage(String images) {
        if (images == null || images.isEmpty()) return "";
        int i = images.indexOf(",");
        return i > 0 ? images.substring(0, i) : images;
    }
}
