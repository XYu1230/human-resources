package com.stdu.controller;

import com.stdu.entity.Organization;
import com.stdu.result.Result;
import com.stdu.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织架构表 前端控制器
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-07
 */
@RestController
@RequestMapping("/api/organization")
@Slf4j
@CrossOrigin(origins = "*")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    /**
     * 获取组织架构树
     * @param companyId 企业ID
     * @return 组织架构树
     */
    @GetMapping("/tree")
    public Result<List<Organization>> getTree(@RequestParam(required = false) Long companyId) {
        log.info("获取组织架构树，companyId={}", companyId);
        return Result.success(organizationService.getOrganizationTree(companyId));
    }

    /**
     * 获取组织架构节点详情
     * @param id 节点ID
     * @return 组织架构节点详情
     */
    @GetMapping("/{id}")
    public Result<Organization> getById(@PathVariable Long id) {
        log.info("获取组织架构节点详情，id={}", id);
        return Result.success(organizationService.getOrganizationById(id));
    }

    /**
     * 新增组织架构节点
     * @param organization 组织架构节点信息
     * @return 新增节点的ID
     */
    @PostMapping
    public Result<Map<String, Long>> add(@RequestBody Organization organization) {
        log.info("新增组织架构节点：{}", organization);
        Long id = organizationService.addOrganization(organization);
        return Result.success(Map.of("id", id));
    }

    /**
     * 更新组织架构节点
     * @param id 节点ID
     * @param organization 组织架构节点信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody Organization organization) {
        log.info("更新组织架构节点，id={}，organization={}", id, organization);
        organizationService.updateOrganization(id, organization);
        return Result.success();
    }

    /**
     * 删除组织架构节点
     * @param id 节点ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除组织架构节点，id={}", id);
        organizationService.deleteOrganization(id);
        return Result.success();
    }

    /**
     * 更新组织架构节点状态
     * @param id 节点ID
     * @param statusMap 状态信息
     * @return 更新结果
     */
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> statusMap) {
        Integer status = statusMap.get("status");
        log.info("更新组织架构节点状态，id={}，status={}", id, status);
        organizationService.updateOrganizationStatus(id, status);
        return Result.success();
    }
}
