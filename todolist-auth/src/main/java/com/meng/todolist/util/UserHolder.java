package com.meng.todolist.util;


import com.meng.todolist.modules.user.models.bo.UserInfoBO;

public class UserHolder {
    private static final ThreadLocal<UserInfoBO> tl = new ThreadLocal<>();

    public static void saveUser(UserInfoBO user) {
        tl.set(user);
    }

    public static UserInfoBO getUser() {
        return tl.get();
    }

    public static void removeUser() {
        tl.remove();
    }
}
