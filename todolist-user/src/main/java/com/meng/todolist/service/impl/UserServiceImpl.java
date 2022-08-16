package com.meng.todolist.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meng.todolist.common.response.ServerResponse;
import com.meng.todolist.common.util.PasswordEncoder;
import com.meng.todolist.common.util.PrincipalUtil;
import com.meng.todolist.modules.auth.service.IAuthenticationService;
import com.meng.todolist.modules.user.models.bo.UserInfoBO;
import com.meng.todolist.modules.user.models.dto.UserRegisterDTO;
import com.meng.todolist.modules.user.models.entity.User;
import com.meng.todolist.modules.user.models.vo.TokenVO;
import com.meng.todolist.modules.user.service.IUserService;
import com.meng.todolist.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UserServiceImpl
 * @Description UserServiceImpl
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public ServerResponse registerAndGetToken(UserRegisterDTO userRegisterDTO) {
        String email = userRegisterDTO.getEmail();
        String password = userRegisterDTO.getPassword();
        if (StrUtil.isBlank(email)) {
            return ServerResponse.showFailMsg("Email should not be null, please check it");
        }
        if (StrUtil.isBlank(password)) {
            return ServerResponse.showFailMsg("Password should not be null, please check it");
        }
        if (!PrincipalUtil.isEmail(email)) {
            return ServerResponse.showFailMsg("The format of email is not correct, please check it");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email);
        User one = this.getOne(userQueryWrapper);
        if (null != one) return ServerResponse.showFailMsg("This email has already been used, please change another");
        Long uid = this.register(userRegisterDTO);

        // Register and login automatically
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setUserId(uid);
        // Get the token, if in microservice sceniros, using feign
        TokenVO tokenVO = authenticationService.storeUserAndGetToken(userInfoBO);
        return ServerResponse.success(tokenVO);
    }

    public Long register(UserRegisterDTO userRegisterDTO) {
        String password = userRegisterDTO.getPassword();
        String encodedPassword = PasswordEncoder.encode(password);
        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(encodedPassword);
        userMapper.insert(user);
        return user.getId();
    }
}
