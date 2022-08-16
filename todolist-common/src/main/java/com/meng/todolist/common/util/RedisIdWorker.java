package com.meng.todolist.common.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName RedisIdWorker
 * @Description Generate global id for the tasks
 * @Author wangmeng
 * @Date 2022/8/16
 */
@Component
public class RedisIdWorker {
    /**
     * Begin timestamp
     */
    private static final long BEGIN_TIMESTAMP = 1640995200L;
    /**
     * Number of bits
     */
    private static final int COUNT_BITS = 10;

    private StringRedisTemplate stringRedisTemplate;

    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public long nextId(String keyPrefix) {
        // Generate the timestamp
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;

        // Generate the sequence number
        // Get the current date
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // self increment
        long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);

        // 3.拼接并返回
        return timestamp << COUNT_BITS | count;
    }
}