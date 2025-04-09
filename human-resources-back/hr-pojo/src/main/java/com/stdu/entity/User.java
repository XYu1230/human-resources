package com.stdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-09
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户名，唯一
     */
    private String username;

    /**
     * 加密存储的密码
     */
    private String password;

    /**
     * 用户类型（1-系统管理员/2-人力资源主管/3-普通用户）
     */
    private String roleType;

    /**
     * 用户真实姓名（可选）
     */
    private String realName;

    /**
     * 状态（0-禁用，1-启用）
     */
    private Byte status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 最近更新时间
     */
    private LocalDateTime updatedAt;
}
