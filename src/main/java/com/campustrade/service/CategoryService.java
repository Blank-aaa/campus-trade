package com.campustrade.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.campustrade.common.PageDTO;
import com.campustrade.entity.Category;
import com.campustrade.entity.Product;
import com.campustrade.mapper.CategoryMapper;
import com.campustrade.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类板块 — 分类列表、按分类查商品
 */
@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    public CategoryService(CategoryMapper categoryMapper, ProductMapper productMapper) {
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
    }

    public List<Category> list() {
        return categoryMapper.selectList(
                new LambdaQueryWrapper<Category>().orderByAsc(Category::getSort));
    }

    public PageDTO<Product> productsByCategory(Integer categoryId, int page, int size) {
        IPage<Product> p = productMapper.selectPage(
                new Page<>(page, size),
                new LambdaQueryWrapper<Product>()
                        .eq(Product::getCategoryId, categoryId)    // 按分类筛选
                        .eq(Product::getStatus, 1)                 // 只要在售
                        .orderByDesc(Product::getCreateTime));     // 最新优先
        return PageDTO.of(p.getTotal(), p.getCurrent(), p.getSize(), p.getRecords());
    }
}
