package com.meng.todolist.common.util;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

/**
 * @ClassName DefaultAuthConfigAdapter
 * @Description Implementation adapter of AuthConfigAdapter according to default scenario
 * @Author wangmeng
 * @Date 2022/8/14
 */
public class PrincipalUtil {

    /**
     * Email regular
     */
    public static final String EMAIL_REGEXP = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";


    /**
     * Whether the string matches the mail rules
     *
     * @param value
     * @return
     */
    public static boolean isEmail(String value) {
        return isMatching(EMAIL_REGEXP, value);
    }


    public static boolean isMatching(String regexp, String value) {
        if (StrUtil.isBlank(value)) {
            return false;
        }
        return Pattern.matches(regexp, value);
    }
}
