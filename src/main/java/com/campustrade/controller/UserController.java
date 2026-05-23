package com.campustrade.controller;

import com.campustrade.common.PageDTO;
import com.campustrade.common.R;
import com.campustrade.entity.Order;
import com.campustrade.entity.Product;
import com.campustrade.entity.User;
import com.campustrade.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 我的板块 API
 * 路径前缀: /api/user
 * 全部接口需要登录态: 请求头 Header 带 userId (Long类型)
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取当前用户信息
     * GET /api/user/info
     * Header: userId (Long, 必填)
     * 返回: R<User>  字段见 User 实体类
     */
    @GetMapping("/info")
    public R<User> info(@RequestHeader("userId") Long userId) {
        return R.ok(userService.info(userId));
    }

    /**
     * 修改用户信息(只传要改的字段即可)
     * PUT /api/user/info
     * Header: userId (Long, 必填)
     * Body(JSON):
     *   nickname   String   昵称(可选)
     *   avatar     String   头像URL(可选)
     *   phone      String   手机号(可选)
     *   school     String   学校(可选)
     * Body示例: {"nickname": "新昵称", "phone": "13900001111", "school": "XX大学"}
     */
    @PutMapping("/info")
    public R<Void> updateInfo(@RequestHeader("userId") Long userId,
                              @RequestBody Map<String, Object> body) {
        userService.updateInfo(userId,
                (String) body.get("nickname"),
                (String) body.get("avatar"),
                (String) body.get("phone"),
                (String) body.get("school"));
        return R.ok();
    }

    /**
     * 发布闲置商品
     * POST /api/user/publish
     * Header: userId (Long, 必填, 即卖家ID)
     * Body(JSON):
     *   title          String      标题(必填)
     *   description    String      商品描述(必填)
     *   price          BigDecimal  售价(必填)
     *   originalPrice  BigDecimal  原价(可选,划线价展示用)
     *   images         String      图片URL,多张逗号分隔(必填,如"a.jpg,b.jpg")
     *   categoryId     Integer     分类ID(必填,对应 /api/category/list 返回的id)
     * Body示例:
     * {"title":"二手iPhone14","description":"九成新无拆修","price":2999,"originalPrice":5999,"images":"a.jpg,b.jpg","categoryId":1}
     */
    @PostMapping("/publish")
    public R<Void> publish(@RequestHeader("userId") Long userId,
                           @RequestBody Map<String, Object> body) {
        userService.publish(userId,
                (String) body.get("title"),
                (String) body.get("description"),
                new BigDecimal(body.get("price").toString()),
                body.get("originalPrice") != null ? new BigDecimal(body.get("originalPrice").toString()) : null,
                (String) body.get("images"),
                Integer.parseInt(body.get("categoryId").toString()));
        return R.ok();
    }

    /**
     * 查询我发布的商品(分页)
     * GET /api/user/published?page=1&size=10
     * Header: userId (Long, 必填)
     * 入参:
     *   page   int   页码(可选,默认1,Query参数)
     *   size   int   每页条数(可选,默认10,Query参数)
     * 返回: R<PageDTO<Product>>
     */
    @GetMapping("/published")
    public R<PageDTO<Product>> myPublished(
            @RequestHeader("userId") Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return R.ok(userService.myPublished(userId, page, size));
    }

    /**
     * 修改我发布的商品(只传要改的字段)
     * PUT /api/user/product/{productId}
     * Header: userId (Long, 必填)
     * Path:  productId (Long, 商品ID)
     * Body(JSON):
     *   title          String      标题(可选)
     *   description    String      描述(可选)
     *   price          BigDecimal  售价(可选)
     *   originalPrice  BigDecimal  原价(可选)
     *   images         String      图片(可选,逗号分隔)
     *   categoryId     Integer     分类ID(可选)
     * Body示例: {"price": 2599, "title": "降价出iPhone14"}
     */
    @PutMapping("/product/{productId}")
    public R<Void> updateProduct(@RequestHeader("userId") Long userId,
                                 @PathVariable Long productId,
                                 @RequestBody Map<String, Object> body) {
        userService.updateProduct(userId, productId,
                (String) body.get("title"),
                (String) body.get("description"),
                body.get("price") != null ? new BigDecimal(body.get("price").toString()) : null,
                body.get("originalPrice") != null ? new BigDecimal(body.get("originalPrice").toString()) : null,
                (String) body.get("images"),
                body.get("categoryId") != null ? Integer.parseInt(body.get("categoryId").toString()) : null);
        return R.ok();
    }

    /**
     * 下架商品(状态改为3-下架)
     * DELETE /api/user/product/{productId}
     * Header: userId (Long, 必填)
     * Path:  productId (Long, 商品ID)
     */
    @DeleteMapping("/product/{productId}")
    public R<Void> offShelf(@RequestHeader("userId") Long userId,
                            @PathVariable Long productId) {
        userService.offShelf(userId, productId);
        return R.ok();
    }

    /**
     * 查询我的订单(买家视角,支持按状态筛选)
     * GET /api/user/orders?status=1&page=1&size=10
     * Header: userId (Long, 必填, 买家ID)
     * 入参:
     *   status   Integer  订单状态(可选,Query参数): 1待发货 2待收货 3已完成 4已取消
     *   page     int      页码(可选,默认1,Query参数)
     *   size     int      每页条数(可选,默认10,Query参数)
     * 返回: R<PageDTO<Order>>  字段见 Order 实体类
     */
    @GetMapping("/orders")
    public R<PageDTO<Order>> myOrders(
            @RequestHeader("userId") Long userId,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return R.ok(userService.myOrders(userId, status, page, size));
    }

    /**
     * 创建订单(直接购买,购买后商品状态自动变为已售)
     * POST /api/user/order/create
     * Header: userId (Long, 必填, 买家ID)
     * Body(JSON):
     *   productId   Long    商品ID(必填)
     *   quantity    int     购买数量(可选,默认1)
     *   remark      String  买家备注(可选,默认空字符串)
     * Body示例: {"productId": 1, "quantity": 1, "remark": "请尽快发货"}
     * 返回: R<String>  — data为订单编号(如"CT20260520120000a1b2c3")
     */
    @PostMapping("/order/create")
    public R<String> createOrder(@RequestHeader("userId") Long userId,
                                 @RequestBody Map<String, Object> body) {
        Long productId = Long.valueOf(body.get("productId").toString());
        int quantity = body.containsKey("quantity") ? Integer.parseInt(body.get("quantity").toString()) : 1;
        String remark = (String) body.getOrDefault("remark", "");
        String orderNo = userService.createOrder(userId, productId, quantity, remark);
        return R.ok(orderNo);
    }
}
