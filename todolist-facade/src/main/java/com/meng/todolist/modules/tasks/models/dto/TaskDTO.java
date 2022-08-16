package com.meng.todolist.modules.tasks.models.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @ClassName TaskDTO
 * @Description TaskDTO
 * @Author wangmeng
 * @Date 2022/8/14
 */
@Data
public class TaskDTO {

    private Long id;

    private String topic;

    /**
     * 0 to do 1 in progress 2 complete
     */
    private Integer statusIndex;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private String dueTime;

    private String attachmentURL;

}
