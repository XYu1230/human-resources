package com.stdu.mapper;

import com.stdu.entity.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


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

}
