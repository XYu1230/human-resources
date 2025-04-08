package com.stdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 部门信息表
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-07
 */
@Data
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 所属组织ID
     */
    private Long companyId;

    /**
     * 部门负责人ID（员工）
     */
    private Long leaderId;

    /**
     * 部门状态(1启用/0禁用)
     */
    private int status;
}
