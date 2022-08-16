package com.meng.todolist.modules.tasks.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName Task
 * @Description Task
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Data
public class Task {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long redisId;

    private String topic;

    private Long userId;

    private String attachmentURL;

    private LocalDateTime dueTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 0 not yet 1 completed
     */
    private int isCompleted;
}
