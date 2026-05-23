package com.campustrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
public class CampusTradeApplication {
    public static void main(String[] args) {
        // 启动时自动创建数据库(社区版IDEA没有数据库工具,这里代劳)
        String url = "jdbc:mysql://localhost:3306/?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
        try (Connection conn = DriverManager.getConnection(url, "root", "123456");
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS campus_trade DEFAULT CHARACTER SET utf8mb4");
            System.out.println("=== 数据库 campus_trade 已就绪 ===");
        } catch (Exception e) {
            System.err.println("=== 建库失败,请检查MySQL是否启动: " + e.getMessage() + " ===");
        }
        SpringApplication.run(CampusTradeApplication.class, args);
    }
}
