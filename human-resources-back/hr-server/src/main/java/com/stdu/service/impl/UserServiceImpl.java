package com.stdu.service.impl;

import com.stdu.constant.MessageConstant;
import com.stdu.dto.UserLoginDTO;
import com.stdu.entity.User;
import com.stdu.exception.LoginFailedException;
import com.stdu.mapper.UserMapper;
import com.stdu.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-09
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        User user = userMapper.selectByName(userLoginDTO.getUsername());

        if(user == null){
            throw new LoginFailedException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        if(!user.getPassword().equals(userLoginDTO.getPassword())){
            throw new LoginFailedException(MessageConstant.PASSWORD_ERROR);
        }

        if(user.getStatus() != 1){
            throw new LoginFailedException(MessageConstant.ACCOUNT_LOCKED);
        }

        return user;
    }
}
