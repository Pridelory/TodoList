package com.meng.todolist.common.constant;

/**
 * @ClassName RedisConstants
 * @Description RedisConstants
 * @Author wangmeng
 * @Date 2022/8/13
 */
public interface RedisConstants {

    String LOGIN_USER_KEY = "login:token:";

    Long LOGIN_USER_TTL = 36000L;

    String TOPIC_DATA_KEY = "topic:data:";
}
