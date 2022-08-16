package com.meng.todolist.common.util;

import cn.hutool.core.util.RandomUtil;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName PasswordEncoder
 * @Description PasswordEncoder
 * @Author wangmeng
 * @Date 2022/8/13
 */
public class PasswordEncoder {

    public static String encode(String password) {
        // generate salt
        String salt = RandomUtil.randomString(20);
        return encode(password, salt);
    }

    /**
     * MD5 encryption
     * @param password
     * @param salt
     * @return
     */
    private static String encode(String password, String salt) {
        // Encryption
        return salt + "@" + DigestUtils.md5DigestAsHex((password + salt).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Judge if matches
     * @param encodedPassword
     * @param rawPassword
     * @return
     */
    public static Boolean matches(String encodedPassword, String rawPassword) {
        if (encodedPassword == null || rawPassword == null) {
            return false;
        }
        if (!encodedPassword.contains("@")) {
            throw new RuntimeException("The password format is incorrect!");
        }
        String[] arr = encodedPassword.split("@");
        // Get the salt
        String salt = arr[0];
        // Compare
        return encodedPassword.equals(encode(rawPassword, salt));
    }
}
