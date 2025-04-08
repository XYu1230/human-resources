package com.stdu.controller;

import com.stdu.common.Result;
import com.stdu.entity.Department;
import com.stdu.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门信息表 前端控制器
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-07
 */
@RestController
@RequestMapping("/api/department")
@Slf4j
@CrossOrigin(origins = "*")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取公司下的所有部门列表
     * @param companyId 公司ID
     * @return 部门列表
     */
    @GetMapping("/list/{companyId}")
    public Result<List<Department>> list(@PathVariable Long companyId) {
        return Result.success(departmentService.findAllDepartmentById(companyId));
    }

    /**
     * 根据ID获取部门信息
     * @param id 部门ID
     * @return 部门信息
     */
    @GetMapping("/{id}")
    public Result<Department> get(@PathVariable Long id) {
        return Result.success(departmentService.findDepartmentById(id));
    }

    /**
     * 新增部门信息
     * @param department 部门信息
     * @return 新增的部门信息
     */
    @PostMapping()
    public Result<Department> save(@RequestBody Department department) {
        return Result.success(departmentService.saveDepartment(department));
    }

    /**
     * 更新部门信息
     * @param department 部门信息
     * @return 更新后的部门信息
     */
    @PutMapping()
    public Result<Department> update(@RequestBody Department department) {
        return Result.success(departmentService.updateDepartment(department));
    }

    /**
     * 删除部门信息
     * @param id 部门ID
     * @return 空结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
        return Result.success();
    }

    /**
     * 更新部门状态
     * @param id 部门ID
     * @return 更新后的部门信息
     */
    @PutMapping("/{id}/status")
    public Result<Department> updateStatus(@PathVariable Long id) {
        return Result.success(departmentService.updateDepartmentStatus(id));
    }

    /**
     * 获取公司下的简化部门列表
     * @param companyId 公司ID
     * @return 简化部门列表
     */
    @GetMapping("/employee/simple/company/{companyId}")
    public List<Department> getSimpleCompany(@PathVariable Long companyId) {
        return null;
    }
}
