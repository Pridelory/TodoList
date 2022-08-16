package com.meng.todolist.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meng.todolist.common.response.ServerResponse;
import com.meng.todolist.modules.user.models.dto.UserRegisterDTO;
import com.meng.todolist.modules.user.models.entity.User;

/**
 * @ClassName IUserService
 * @Description TODO
 * @Author wangmeng
 * @Date 2022/8/13
 */
public interface IUserService extends IService<User> {

    ServerResponse registerAndGetToken(UserRegisterDTO userRegisterDTO);
}
