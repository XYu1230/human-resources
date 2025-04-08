package com.stdu.service;

import com.stdu.entity.Organization;

import java.util.List;

/**
 * <p>
 * 组织架构表 服务类
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-07
 */
public interface OrganizationService{

    /**
     * 获取组织架构树
     * @param companyId 企业ID
     * @return 组织架构树列表
     */
    List<Organization> getOrganizationTree(Long companyId);
    
    /**
     * 获取组织架构节点详情
     * @param id 节点ID
     * @return 组织架构节点详情
     */
    Organization getOrganizationById(Long id);
    
    /**
     * 新增组织架构节点
     * @param organization 组织架构节点信息
     * @return 新增节点的ID
     */
    Long addOrganization(Organization organization);
    
    /**
     * 更新组织架构节点
     * @param id 节点ID
     * @param organization 组织架构节点信息
     */
    void updateOrganization(Long id, Organization organization);
    
    /**
     * 删除组织架构节点
     * @param id 节点ID
     */
    void deleteOrganization(Long id);
    
    /**
     * 更新组织架构节点状态
     * @param id 节点ID
     * @param status 状态值
     */
    void updateOrganizationStatus(Long id, Integer status);
    
    /**
     * 移动组织架构节点
     * @param id 节点ID
     * @param targetParentId 目标父节点ID
     */
    void moveOrganization(Long id, Long targetParentId);
}
