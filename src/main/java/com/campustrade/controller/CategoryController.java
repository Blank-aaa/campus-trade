package com.campustrade.controller;

import com.campustrade.common.PageDTO;
import com.campustrade.common.R;
import com.campustrade.entity.Category;
import com.campustrade.entity.Product;
import com.campustrade.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类板块 API
 * 路径前缀: /api/category
 * 无需登录态
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取全部分类列表
     * GET /api/category/list
     * 入参: 无
     * 返回: R<List<Category>>
     *   Category.id     Integer  分类ID
     *   Category.name   String   分类名称(如:数码电子、书籍教材)
     *   Category.icon   String   图标(emoji字符)
     *   Category.sort   Integer  排序(越小越靠前)
     */
    @GetMapping("/list")
    public R<List<Category>> list() {
        return R.ok(categoryService.list());
    }

    /**
     * 按分类查询商品(分页)
     * GET /api/category/{categoryId}/products?page=1&size=10
     * 入参:
     *   categoryId   Integer  分类ID(必填,Path参数)
     *   page         int      页码(可选,默认1,Query参数)
     *   size         int      每页条数(可选,默认10,Query参数)
     * 返回: R<PageDTO<Product>>  — 只返回在售商品(status=1)
     */
    @GetMapping("/{categoryId}/products")
    public R<PageDTO<Product>> products(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return R.ok(categoryService.productsByCategory(categoryId, page, size));
    }
}
