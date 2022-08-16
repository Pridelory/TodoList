package com.meng.todolist.modules.auth.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName AuthenticationDTO
 * @Description AuthenticationDTO
 * @Author wangmeng
 * @Date 2022/8/14
 */
@Data
public class AuthenticationDTO {

    @NotBlank(message = "Email can not be null")
    private String principal;

    @NotBlank(message = "Credentials can not be null")
    private String credentials;
}
