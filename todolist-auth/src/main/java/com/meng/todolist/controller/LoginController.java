package com.meng.todolist.controller;

import cn.hutool.core.util.StrUtil;
import com.meng.todolist.common.response.ServerResponse;
import com.meng.todolist.modules.auth.models.dto.AuthenticationDTO;
import com.meng.todolist.modules.auth.service.IAuthenticationService;
import com.meng.todolist.modules.user.models.bo.UserInfoBO;
import com.meng.todolist.modules.user.models.vo.TokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName LoginController
 * @Description Used to handle login scenarios
 * @Author wangmeng
 * @Date 2022/8/14
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "User Login API")
public class LoginController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/login")
    @ApiOperation(value = "Login", notes = "User Login")
    @CrossOrigin
    public ServerResponse login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        String email = authenticationDTO.getPrincipal();
        String password = authenticationDTO.getCredentials();
        if (StrUtil.isBlank(email)) {
            return ServerResponse.showFailMsg("Email should not be null, please check it");
        }
        if (StrUtil.isBlank(password)) {
            return ServerResponse.showFailMsg("Password should not be null, please check it");
        }

        ServerResponse<UserInfoBO> userInfoBOServerResponse = authenticationService.validateUserAndGetToken(authenticationDTO.getPrincipal(), authenticationDTO.getCredentials());
        if (!userInfoBOServerResponse.isSuccess()) return ServerResponse.transform(userInfoBOServerResponse);
        TokenVO tokenVO = authenticationService.storeUserAndGetToken(userInfoBOServerResponse.getData());
        return ServerResponse.success(tokenVO);
    }
}
