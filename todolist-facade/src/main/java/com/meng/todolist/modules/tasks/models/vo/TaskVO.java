package com.meng.todolist.modules.tasks.models.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName TaskVO
 * @Description TaskVO
 * @Author wangmeng
 * @Date 2022/8/14
 */
@Data
public class TaskVO {

    private Long id;

    private String topic;

    private Long userId;

    private String attachmentURL;

    private String dueTime;

    private String createTime;

    private String updateTime;

    /**
     * 0 not yet 1 completed
     */
    private int status;
}
