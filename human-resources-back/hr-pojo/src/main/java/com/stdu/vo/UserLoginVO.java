package com.stdu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginVO {

    private Long id;
    private String username;
    private String realName;
    // jwt令牌
    private String token;
}
