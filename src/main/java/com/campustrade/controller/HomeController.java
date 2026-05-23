package com.campustrade.controller;

import com.campustrade.common.PageDTO;
import com.campustrade.common.R;
import com.campustrade.entity.Banner;
import com.campustrade.entity.Product;
import com.campustrade.service.HomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 首页板块 API
 * 路径前缀: /api/home
 * 无需登录态
 */
@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    /**
     * 获取首页轮播图
     * GET /api/home/banner
     * 入参: 无
     * 返回: R<List<Banner>>
     *   Banner.id         Integer  轮播图ID
     *   Banner.imageUrl   String   轮播图图片地址
     *   Banner.linkUrl    String   点击跳转链接(可能为空)
     *   Banner.sort       Integer  排序值(越小越靠前)
     */
    @GetMapping("/banner")
    public R<List<Banner>> banner() {
        return R.ok(homeService.banners());
    }

    /**
     * 获取热门商品(按浏览量排序,展示前10条)
     * GET /api/home/hot
     * 入参: 无
     * 返回: R<List<Product>>  — 只返回在售商品(status=1),按 viewCount 降序
     */
    @GetMapping("/hot")
    public R<List<Product>> hot() {
        return R.ok(homeService.hotProducts());
    }

    /**
     * 搜索商品(模糊匹配标题)
     * GET /api/home/search?keyword=手机&page=1&size=10
     * 入参:
     *   keyword   String   搜索关键词(可选,Query参数)
     *   page      int      页码(可选,默认1,Query参数)
     *   size      int      每页条数(可选,默认10,Query参数)
     * 返回: R<PageDTO<Product>>
     *   PageDTO.total    Long        总条数
     *   PageDTO.page     Long        当前页码
     *   PageDTO.size     Long        每页条数
     *   PageDTO.records  List<Product>  商品列表
     */
    @GetMapping("/search")
    public R<PageDTO<Product>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return R.ok(homeService.search(keyword, page, size));
    }
}
