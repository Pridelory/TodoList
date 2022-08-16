package com.meng.todolist.util;

import cn.hutool.core.lang.UUID;
import org.springframework.stereotype.Component;

/**
 * @ClassName TokenManagement
 * @Description TODO
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Component
public class TokenManagement {

    public static String generateToken() {
        return UUID.randomUUID().toString(true);
    }
}
