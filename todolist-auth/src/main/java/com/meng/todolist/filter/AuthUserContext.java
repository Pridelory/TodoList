package com.meng.todolist.filter;

import com.meng.todolist.modules.user.models.bo.UserInfoBO;

/**
 * @ClassName AuthUserContext
 * @Description The context of the auth user, used a thread local variable to store auth user
 * @Author wangmeng
 * @Date 2022/8/14
 */
public class AuthUserContext {

    private static final ThreadLocal<UserInfoBO> USER_INFO_IN_TOKEN_HOLDER = new ThreadLocal<>();

    public static UserInfoBO get() {
        return USER_INFO_IN_TOKEN_HOLDER.get();
    }

    public static void set(UserInfoBO userInfoInTokenBo) {
        USER_INFO_IN_TOKEN_HOLDER.set(userInfoInTokenBo);
    }

    public static void clean() {
        if (USER_INFO_IN_TOKEN_HOLDER.get() != null) {
            USER_INFO_IN_TOKEN_HOLDER.remove();
        }
    }

}
