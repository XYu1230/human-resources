package com.stdu.service.impl;

import com.stdu.entity.Department;

import com.stdu.mapper.DepartmentMapper;
import com.stdu.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> findAllDepartmentById(Long id) {
        try {
            if (id == null) {
                return departmentMapper.findAllActiveDepartments();
            }
            return departmentMapper.findSubDepartmentsById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("查询部门信息失败", e);
        } catch (Exception e) {
            throw new RuntimeException("获取部门列表时发生未知错误", e);
        }
    }

    @Override
    public Department findDepartmentById(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("部门ID不能为空且必须大于0");
        }

        try {
            Department department = departmentMapper.selectById(id);
            if (department == null) {
                throw new RuntimeException("未找到ID为 " + id + " 的部门");
            }
            return department;
        } catch (DataAccessException e) {
            throw new RuntimeException("查询部门信息失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Department saveDepartment(Department department) {
        if (department == null) {
            throw new RuntimeException("部门信息不能为空");
        }
        if (!StringUtils.hasText(department.getName())) {
            throw new RuntimeException("部门名称不能为空");
        }

        try {
            department.setStatus(1); // 默认启用状态

            int result = departmentMapper.insert(department);
            if (result <= 0) {
                throw new RuntimeException("新增部门信息失败");
            }
            return department;
        } catch (DataAccessException e) {
            throw new RuntimeException("新增部门信息时数据库错误", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Department updateDepartment(Department department) {
        if (department == null || department.getId() == null) {
            throw new RuntimeException("部门信息和部门ID不能为空");
        }
        if (!StringUtils.hasText(department.getName())) {
            throw new RuntimeException("部门名称不能为空");
        }

        try {
            Department existingDept = departmentMapper.selectById(department.getId());
            if (existingDept == null) {
                throw new RuntimeException("未找到ID为 " + department.getId() + " 的部门");
            }

            int result = departmentMapper.updateById(department);
            if (result <= 0) {
                throw new RuntimeException("更新部门信息失败");
            }
            return department;
        } catch (DataAccessException e) {
            throw new RuntimeException("更新部门信息时数据库错误", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDepartmentById(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("部门ID不能为空且必须大于0");
        }

        try {
            Department department = departmentMapper.selectById(id);
            if (department == null) {
                throw new RuntimeException("未找到ID为 " + id + " 的部门");
            }

            int result = departmentMapper.deleteById(id);
            if (result <= 0) {
                throw new RuntimeException("删除部门信息失败");
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("删除部门信息时数据库错误", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Department updateDepartmentStatus(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("部门ID不能为空且必须大于0");
        }

        try {
            Department department = departmentMapper.selectById(id);
            if (department == null) {
                throw new RuntimeException("未找到ID为 " + id + " 的部门");
            }

            int newStatus = department.getStatus() == 1 ? 0 : 1;
            department.setStatus(newStatus);

            int result = departmentMapper.updateById(department);
            if (result <= 0) {
                throw new RuntimeException("更新部门状态失败");
            }
            return department;
        } catch (DataAccessException e) {
            throw new RuntimeException("更新部门状态时数据库错误", e);
        }
    }
}
