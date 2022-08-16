package com.meng.todolist.modules.tasks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meng.todolist.common.response.ServerResponse;
import com.meng.todolist.modules.tasks.models.dto.TaskDTO;
import com.meng.todolist.modules.tasks.models.entity.Task;
import com.meng.todolist.modules.tasks.models.vo.TaskVO;
import com.meng.todolist.modules.user.models.bo.UserInfoBO;

import java.util.List;

/**
 * @ClassName ITaskService
 * @Description Task related operations
 * @Author wangmeng
 * @Date 2022/8/13
 */
public interface ITaskService extends IService<Task> {

    /**
     * Add task to redis
     * @param userInfoBO
     * @param taskDTO
     * @return
     */
    ServerResponse addTaskToRedis(UserInfoBO userInfoBO, TaskDTO taskDTO);

    /**
     * Update the task to redis
     * @param userInfoBO
     * @param taskDTO
     * @return
     */
    ServerResponse updateTaskToRedis(UserInfoBO userInfoBO, TaskDTO taskDTO);

    /**
     * Delete a task to redis
     * @param userInfoBO
     * @param taskDTO
     * @return
     */
    ServerResponse deleteTaskToRedis(UserInfoBO userInfoBO, TaskDTO taskDTO);

    /**
     * Get today todolist of a given user from redis
     * @param userInfoBO
     * @return
     */
    ServerResponse<List<TaskVO>> getTodayTaskList(UserInfoBO userInfoBO);
}
