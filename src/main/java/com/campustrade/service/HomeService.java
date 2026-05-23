package com.campustrade.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campustrade.common.PageDTO;
import com.campustrade.entity.Banner;
import com.campustrade.entity.Product;
import com.campustrade.mapper.BannerMapper;
import com.campustrade.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页板块 — 轮播图、热门商品、搜索
 */
@Service
public class HomeService {

    private final BannerMapper bannerMapper;
    private final ProductMapper productMapper;

    public HomeService(BannerMapper bannerMapper, ProductMapper productMapper) {
        this.bannerMapper = bannerMapper;
        this.productMapper = productMapper;
    }

    public List<Banner> banners() {
        return bannerMapper.selectList(
                new LambdaQueryWrapper<Banner>().orderByAsc(Banner::getSort));
    }

    public List<Product> hotProducts() {
        return productMapper.selectList(
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getStatus, 1)             // 只要在售商品
                        .orderByDesc(Product::getViewCount)     // 按浏览量降序
                        .last("LIMIT 10"));                     // 只取前10条
    }

    public PageDTO<Product> search(String keyword, int page, int size) {
        IPage<Product> p = productMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getStatus, 1)             // 只搜在售商品
                        .like(keyword != null && !keyword.isEmpty(), Product::getTitle, keyword)
                        .orderByDesc(Product::getCreateTime)); // 最新优先
        return PageDTO.of(p.getTotal(), p.getCurrent(), p.getSize(), p.getRecords());
    }
}
