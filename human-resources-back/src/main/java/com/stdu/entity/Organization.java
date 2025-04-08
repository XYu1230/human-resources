package com.stdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * <p>
 * 组织架构表
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-08
 */
@Data
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 上级组织ID
     */
    private Long parentId;

    /**
     * 组织层级
     */
    private Integer level;

    /**
     * 所属企业ID
     */
    private Long companyId;

    /**
     * 组织状态(1启用/0禁用)
     */
    private Byte status;

    /**
     * 子组织列表
     */
    @TableField(exist = false)
    private List<Organization> children;
}
