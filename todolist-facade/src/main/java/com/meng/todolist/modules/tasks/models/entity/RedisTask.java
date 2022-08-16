package com.meng.todolist.modules.tasks.models.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName RedisTask
 * @Description RedisTask
 * @Author wangmeng
 * @Date 2022/8/14
 */
@Data
public class RedisTask {

    private String id;

    private String topic;

    private Long userId;

    private String attachmentURL;

    private String dueTime;

    private String createTime;

    private String updateTime;

    /**
     * 0 to do 1 progressing 2 complete
     */
    private int status;
}
