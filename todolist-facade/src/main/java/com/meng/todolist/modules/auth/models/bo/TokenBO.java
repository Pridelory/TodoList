package com.meng.todolist.modules.auth.models.bo;

import com.meng.todolist.modules.user.models.bo.UserInfoBO;
import lombok.Data;

/**
 * @ClassName TokenBO
 * @Description TokenBO
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Data
public class TokenBO {

    private UserInfoBO userInfoInToken;

    private String accessToken;

    private String refreshToken;

    private Integer expiresTime;
}
