package com.stdu.service;

import com.stdu.dto.UserLoginDTO;
import com.stdu.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-09
 */
public interface IUserService {

    User login(UserLoginDTO userLoginDTO);
}
