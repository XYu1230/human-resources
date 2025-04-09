package com.stdu.mapper;

import com.stdu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User selectByName(String username);
}
