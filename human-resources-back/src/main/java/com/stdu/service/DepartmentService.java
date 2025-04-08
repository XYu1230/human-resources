package com.stdu.service;

import com.stdu.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门信息表 服务类
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-07
 */
public interface DepartmentService {

    List<Department> findAllDepartmentById(Long id);
    Department findDepartmentById(Long id);
    Department saveDepartment(Department department);
    Department updateDepartment(Department department);
    void deleteDepartmentById(Long id);
    Department updateDepartmentStatus(Long id);




}
