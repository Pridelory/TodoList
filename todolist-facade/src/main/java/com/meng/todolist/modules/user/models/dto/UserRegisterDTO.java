package com.meng.todolist.modules.user.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName UserRegisterDTO
 * @Description UserRegisterDTO
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Data
public class UserRegisterDTO {

    @NotBlank(message = "Email can not be null")
    private String email;

    @NotBlank(message = "Password can not be null")
    private String password;

    private Long userId;
}
