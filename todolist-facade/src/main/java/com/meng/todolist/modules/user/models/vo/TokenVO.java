package com.meng.todolist.modules.user.models.vo;

import lombok.Data;

/**
 * @ClassName TokenVO
 * @Description TokenVO
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Data
public class TokenVO {

    private String accessToken;

    private String refreshToken;

    private Integer expiresIn;

}
