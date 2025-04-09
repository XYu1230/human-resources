package com.stdu.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stdu.constant.JwtClaimsConstant;
import com.stdu.dto.UserLoginDTO;
import com.stdu.entity.User;
import com.stdu.properties.JwtProperties;
import com.stdu.result.Result;
import com.stdu.service.IUserService;
import com.stdu.utils.JwtUtil;
import com.stdu.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author XYu1230
 * @since 2025-04-09
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("用户登录，{}",userLoginDTO) ;
        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .realName(user.getRealName())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }

}
