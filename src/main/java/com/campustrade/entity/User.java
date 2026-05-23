package com.campustrade.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

/**
 * 用户实体
 */
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;                // 用户ID (Long)

    private String openid;          // 微信openid,唯一标识 (String)

    private String nickname;        // 昵称 (String)

    private String avatar;          // 头像图片URL (String)

    private String phone;           // 手机号 (String)

    private String school;          // 学校名称 (String)

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;   // 注册时间,自动填充 (String,ISO格式)

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;   // 更新时间,自动填充 (String,ISO格式)

    @TableLogic
    private Integer deleted;            // 逻辑删除标记: 0正常 1已删除 (Integer)

    // ===== getter/setter =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOpenid() { return openid; }
    public void setOpenid(String openid) { this.openid = openid; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
}
