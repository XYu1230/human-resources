package com.stdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>
 * 企业信息表
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-05
 */
@Data
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 企业ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 企业名称
     */
    private String name;

    /**
     * 企业编码
     */
    private String code;

    /**
     * 企业地址
     */
    private String address;

    /**
     * 联系人姓名
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 企业状态(1启用/0禁用)
     */
    private Byte status;
}
