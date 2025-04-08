package com.stdu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.stdu.entity.Organization;
import com.stdu.mapper.OrganizationMapper;
import com.stdu.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织架构表 服务实现类
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-07
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;
    @Override
    public List<Organization> getOrganizationTree(Long companyId) {
        // 获取企业下所有组织架构节点
        LambdaQueryWrapper<Organization> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(companyId != null, Organization::getCompanyId, companyId);
        List<Organization> allOrganizations = organizationMapper.selectList(queryWrapper);
        
        // 构建树形结构
        // 1. 找出所有顶级节点
        List<Organization> rootNodes = allOrganizations.stream()
                .filter(org -> org.getParentId() == 0)
                .collect(Collectors.toList());
        
        // 2. 构建子树
        for (Organization rootNode : rootNodes) {
            buildChildrenTree(rootNode, allOrganizations);
        }
        
        return rootNodes;
    }
    
    /**
     * 递归构建子树
     */
    private void buildChildrenTree(Organization parent, List<Organization> allOrganizations) {
        List<Organization> children = allOrganizations.stream()
                .filter(org -> parent.getId().equals(org.getParentId()))
                .collect(Collectors.toList());
        
        if (!children.isEmpty()) {
            // 设置子节点
            parent.setChildren(children);
            // 递归构建每个子节点的子树
            for (Organization child : children) {
                buildChildrenTree(child, allOrganizations);
            }
        } else {
            parent.setChildren(new ArrayList<>());
        }
    }

    @Override
    public Organization getOrganizationById(Long id) {
        Organization organization = organizationMapper.selectById(id);
        Assert.notNull(organization, "组织架构节点不存在");
        return organization;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addOrganization(Organization organization) {
        // 验证父节点是否存在
        if (organization.getParentId() != null && organization.getParentId() != 0) {
            Organization parentOrg = organizationMapper.selectById(organization.getParentId());
            Assert.notNull(parentOrg, "父节点不存在");
            // 设置层级为父节点层级+1
            organization.setLevel(parentOrg.getLevel() + 1);
        } else {
            // 顶级节点层级为1
            organization.setParentId(0L);
            organization.setLevel(1);
        }
        
        // 保存组织架构节点
        organizationMapper.insert(organization);
        return organization.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrganization(Long id, Organization organization) {
        // 验证节点是否存在
        Organization existingOrg = organizationMapper.selectById(id);
        Assert.notNull(existingOrg, "组织架构节点不存在");
        
        // 验证父节点是否存在且不是自己
        if (organization.getParentId() != null && organization.getParentId() != 0) {
            Assert.isTrue(!id.equals(organization.getParentId()), "父节点不能是自己");
            Organization parentOrg = organizationMapper.selectById(organization.getParentId());
            Assert.notNull(parentOrg, "父节点不存在");
            // 设置层级为父节点层级+1
            organization.setLevel(parentOrg.getLevel() + 1);
        } else {
            // 顶级节点层级为1
            organization.setParentId(0L);
            organization.setLevel(1);
        }
        
        // 设置ID和企业ID
        organization.setId(id);
        organization.setCompanyId(existingOrg.getCompanyId());
        
        // 更新组织架构节点
        organizationMapper.updateById(organization);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrganization(Long id) {
        // 验证节点是否存在
        Organization organization = organizationMapper.selectById(id);
        Assert.notNull(organization, "组织架构节点不存在");
        
        // 验证是否有子节点
        LambdaQueryWrapper<Organization> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Organization::getParentId, id);
        long count = organizationMapper.selectCount(queryWrapper);
        Assert.isTrue(count == 0, "该节点下有子节点，不能删除");
        
        // 删除组织架构节点
        organizationMapper.deleteById(id);
    }

    @Override
    public void updateOrganizationStatus(Long id, Integer status) {
        // 验证节点是否存在
        Organization organization = organizationMapper.selectById(id);
        Assert.notNull(organization, "组织架构节点不存在");
        
        // 验证状态值是否合法
        Assert.isTrue(status == 0 || status == 1, "状态值不合法");
        
        // 更新状态
        LambdaUpdateWrapper<Organization> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Organization::getId, id)
                .set(Organization::getStatus, status.byteValue());
        organizationMapper.update(null, updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveOrganization(Long id, Long targetParentId) {
        // 验证节点是否存在
        Organization organization = organizationMapper.selectById(id);
        Assert.notNull(organization, "组织架构节点不存在");
        
        // 验证目标父节点是否存在
        Organization targetParent = organizationMapper.selectById(targetParentId);
        Assert.notNull(targetParent, "目标父节点不存在");
        
        // 验证目标父节点不是自己或自己的子节点
        Assert.isTrue(!id.equals(targetParentId), "目标父节点不能是自己");
        
        // 更新父节点ID和层级
        organization.setParentId(targetParentId);
        organization.setLevel(targetParent.getLevel() + 1);
        organizationMapper.updateById(organization);
    }
}
