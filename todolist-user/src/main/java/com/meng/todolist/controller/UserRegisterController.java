package com.meng.todolist.controller;

import com.meng.todolist.common.response.ServerResponse;
import com.meng.todolist.modules.user.models.dto.UserRegisterDTO;
import com.meng.todolist.modules.user.models.vo.TokenVO;
import com.meng.todolist.modules.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName UserRegisterController
 * @Description UserRegisterController
 * @Author wangmeng
 * @Date 2022/8/13
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "User Register API")
public class UserRegisterController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "Register", notes = "User Registration")
    @CrossOrigin
    public ServerResponse<TokenVO> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        ServerResponse serverResponse = userService.registerAndGetToken(userRegisterDTO);
        if (!serverResponse.isSuccess()) {
            return ServerResponse.transform(serverResponse);
        }
        return ServerResponse.success((TokenVO) serverResponse.getData());
    }
}
