package com.meng.todolist.modules.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meng.todolist.common.response.ServerResponse;
import com.meng.todolist.modules.user.models.bo.UserInfoBO;
import com.meng.todolist.modules.user.models.entity.User;
import com.meng.todolist.modules.user.models.vo.TokenVO;

/**
 * @ClassName IAuthenticationService
 * @Description Authentication related operations
 * @Author wangmeng
 * @Date 2022/8/13
 */
public interface IAuthenticationService extends IService<User> {

    /**
     * Store the current user to the redis and get the token
     * used after registration successfully
     * @param userInfoBO
     * @return
     */
    TokenVO storeUserAndGetToken(UserInfoBO userInfoBO);

    /**
     * Validate the user and get the token if login successfully
     * // TODO: 2022/8/13 demo version, not complete
     * @param email
     * @param password
     * @return
     */
    ServerResponse<UserInfoBO> validateUserAndGetToken(String email, String password);

    /**
     * Get the user info from redis according to the token
     * @param token
     * @return
     */
    ServerResponse<UserInfoBO> getUserByToken(String token);
}
