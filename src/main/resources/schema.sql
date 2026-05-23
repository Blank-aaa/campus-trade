-- ============================================
-- 校园闲置交易服务系统 数据库建表(MySQL)
-- 启动项目后自动建库建表并写入初始数据
-- ============================================

CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    `openid` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '微信openid',
    `nickname` VARCHAR(64) DEFAULT '' COMMENT '昵称',
    `avatar` VARCHAR(512) DEFAULT '' COMMENT '头像URL',
    `phone` VARCHAR(20) DEFAULT '' COMMENT '手机号',
    `school` VARCHAR(64) DEFAULT '' COMMENT '学校',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除:0正常 1已删除',
    UNIQUE KEY `uk_openid` (`openid`)
) COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `category` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
    `name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '分类名称',
    `icon` VARCHAR(256) DEFAULT '' COMMENT '图标',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除'
) COMMENT='商品分类表';

CREATE TABLE IF NOT EXISTS `product` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '商品ID',
    `title` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '标题',
    `description` TEXT COMMENT '描述',
    `price` DECIMAL(10,2) NOT NULL COMMENT '售价',
    `original_price` DECIMAL(10,2) DEFAULT NULL COMMENT '原价',
    `images` VARCHAR(2048) DEFAULT '' COMMENT '图片,逗号分隔',
    `category_id` INT NOT NULL COMMENT '分类ID',
    `user_id` BIGINT NOT NULL COMMENT '发布者ID',
    `status` TINYINT DEFAULT 1 COMMENT '1在售 2已售 3下架',
    `view_count` INT DEFAULT 0 COMMENT '浏览量',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX `idx_category` (`category_id`),
    INDEX `idx_user` (`user_id`)
) COMMENT='商品表';

CREATE TABLE IF NOT EXISTS `cart_item` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '购物车项ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `quantity` INT DEFAULT 1 COMMENT '数量',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uk_user_product` (`user_id`, `product_id`)
) COMMENT='购物车表';

CREATE TABLE IF NOT EXISTS `order` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
    `order_no` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '订单编号',
    `buyer_id` BIGINT NOT NULL COMMENT '买家ID',
    `seller_id` BIGINT NOT NULL COMMENT '卖家ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `product_title` VARCHAR(128) DEFAULT '' COMMENT '商品标题快照',
    `product_image` VARCHAR(512) DEFAULT '' COMMENT '商品图片快照',
    `price` DECIMAL(10,2) NOT NULL COMMENT '成交单价',
    `quantity` INT DEFAULT 1 COMMENT '数量',
    `total_amount` DECIMAL(10,2) NOT NULL COMMENT '总金额',
    `status` TINYINT DEFAULT 1 COMMENT '1待发货 2待收货 3已完成 4已取消',
    `remark` VARCHAR(256) DEFAULT '' COMMENT '备注',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY `uk_order_no` (`order_no`),
    INDEX `idx_buyer` (`buyer_id`),
    INDEX `idx_seller` (`seller_id`)
) COMMENT='订单表';

CREATE TABLE IF NOT EXISTS `banner` (
    `id` INT AUTO_INCREMENT PRIMARY KEY COMMENT '轮播图ID',
    `image_url` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '图片地址',
    `link_url` VARCHAR(512) DEFAULT '' COMMENT '跳转链接',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除'
) COMMENT='轮播图表';

-- 初始数据(分类)
INSERT IGNORE INTO `category` (`id`, `name`, `icon`, `sort`) VALUES
(1, '数码电子', '📱', 1),
(2, '书籍教材', '📚', 2),
(3, '生活用品', '🏠', 3),
(4, '服饰美妆', '👗', 4),
(5, '运动户外', '⚽', 5),
(6, '其他', '📦', 6);

-- 初始数据(轮播图)
INSERT IGNORE INTO `banner` (`id`, `image_url`, `link_url`, `sort`) VALUES
(1, 'https://img.alicdn.com/example/banner1.jpg', '', 1),
(2, 'https://img.alicdn.com/example/banner2.jpg', '', 2),
(3, 'https://img.alicdn.com/example/banner3.jpg', '', 3);
