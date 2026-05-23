package com.campustrade.controller;

import com.campustrade.common.R;
import com.campustrade.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 购物车板块 API
 * 路径前缀: /api/cart
 * 全部接口需要登录态: 请求头 Header 带 userId (Long类型)
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 获取当前用户的购物车列表(含商品详情)
     * GET /api/cart/list
     * Header: userId (Long, 必填)
     * 入参: 无
     * 返回: R<List<Map>>
     *   cartId      Long        购物车项ID(用于修改/删除)
     *   productId   Long        商品ID
     *   title       String      商品标题
     *   price       BigDecimal  商品单价
     *   image       String      商品首张图片URL
     *   quantity    Integer     购买数量
     */
    @GetMapping("/list")
    public R<List<Map<String, Object>>> list(@RequestHeader("userId") Long userId) {
        return R.ok(cartService.list(userId));
    }

    /**
     * 添加商品到购物车(已存在则数量累加)
     * POST /api/cart/add
     * Header: userId (Long, 必填)
     * Body(JSON):
     *   productId   Long    商品ID(必填)
     *   quantity    int     数量(可选,默认1)
     * Body示例: {"productId": 1, "quantity": 2}
     */
    @PostMapping("/add")
    public R<Void> add(@RequestHeader("userId") Long userId,
                       @RequestBody Map<String, Object> body) {
        Long productId = Long.valueOf(body.get("productId").toString());
        int quantity = body.containsKey("quantity") ? Integer.parseInt(body.get("quantity").toString()) : 1;
        cartService.add(userId, productId, quantity);
        return R.ok();
    }

    /**
     * 修改购物车项数量(quantity<=0 则自动删除)
     * PUT /api/cart/{cartId}
     * Header: userId (Long, 必填)
     * Path:  cartId (Long, 购物车项ID)
     * Body(JSON):
     *   quantity    int     新数量(必填,<=0时删除)
     * Body示例: {"quantity": 3}
     */
    @PutMapping("/{cartId}")
    public R<Void> updateQuantity(@RequestHeader("userId") Long userId,
                                  @PathVariable Long cartId,
                                  @RequestBody Map<String, Object> body) {
        int quantity = Integer.parseInt(body.get("quantity").toString());
        cartService.updateQuantity(cartId, userId, quantity);
        return R.ok();
    }

    /**
     * 删除购物车项
     * DELETE /api/cart/{cartId}
     * Header: userId (Long, 必填)
     * Path:  cartId (Long, 购物车项ID)
     */
    @DeleteMapping("/{cartId}")
    public R<Void> delete(@RequestHeader("userId") Long userId,
                          @PathVariable Long cartId) {
        cartService.delete(cartId, userId);
        return R.ok();
    }
}
