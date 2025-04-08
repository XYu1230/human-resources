package com.stdu.mapper;

import com.stdu.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * <p>
 * 部门信息表 Mapper 接口
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-07
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

    @Select("SELECT * FROM department WHERE company_id = #{id}")
    List<Department> findSubDepartmentsById(@Param("id") Long id);

    @Select("SELECT * FROM department WHERE status = 1")
    List<Department> findAllActiveDepartments();

}
