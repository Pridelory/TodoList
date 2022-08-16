package com.meng.todolist.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.meng.todolist.common.response.ServerResponse;
import com.meng.todolist.filter.AuthUserContext;
import com.meng.todolist.modules.tasks.models.dto.TaskDTO;
import com.meng.todolist.modules.tasks.models.entity.Task;
import com.meng.todolist.modules.tasks.service.ITaskService;
import com.meng.todolist.modules.user.models.bo.UserInfoBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName TasksController
 * @Description Task related operations
 * @Author wangmeng
 * @Date 2022/8/14
 */
@RestController
@RequestMapping("/api/todolist")
@Api(tags = "User Task API")
public class TasksController {

    @Autowired
    private ITaskService taskService;

    @PostMapping("/addTask")
    @ApiOperation(value = "AddTask", notes = "User Adds Task")
    @CrossOrigin
    public ServerResponse addTask(@RequestBody @Valid TaskDTO taskDTO) {
        UserInfoBO userInfoBO = AuthUserContext.get();
        ServerResponse serverResponse = taskService.addTaskToRedis(userInfoBO, taskDTO);
        if (!serverResponse.isSuccess()) return ServerResponse.showFailMsg("Failed to add task");
        return ServerResponse.success(serverResponse.getData());
    }

    @PostMapping("/updateTask")
    @ApiOperation(value = "UpdateTask", notes = "User Updates Task")
    @CrossOrigin
    public ServerResponse updateTask(@RequestBody @Valid TaskDTO taskDTO) {
        UserInfoBO userInfoBO = AuthUserContext.get();
        ServerResponse serverResponse = taskService.updateTaskToRedis(userInfoBO, taskDTO);
        if (!serverResponse.isSuccess()) return ServerResponse.showFailMsg("Failed to update task");
        return ServerResponse.success(serverResponse.getData());
    }

    @DeleteMapping("/deleteTask")
    @ApiOperation(value = "DeleteTask", notes = "User Deletes Task")
    @CrossOrigin
    public ServerResponse deleteTask(@RequestBody @Valid TaskDTO taskDTO) {
        UserInfoBO userInfoBO = AuthUserContext.get();
        return taskService.deleteTaskToRedis(userInfoBO, taskDTO);
    }

    @GetMapping("/getTodayList")
    @ApiOperation(value = "getTodayList", notes = "User Gets Today Tasks (from Redis)")
    @CrossOrigin
    public ServerResponse getTodayList() {
        UserInfoBO userInfoBO = AuthUserContext.get();
        if (userInfoBO == null) return ServerResponse.showFailMsg("Please login firstly");
        return taskService.getTodayTaskList(userInfoBO);
    }

    @GetMapping("/getHistoryList/{userId}")
    @ApiOperation(value = "getHistoryList", notes = "User Gets History Tasks (from MySQL)")
    @CrossOrigin
    public ServerResponse getHistoryList(@PathVariable Long userId) {
        QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
        taskQueryWrapper.eq("user_id", userId);
        List<Task> list = taskService.list(taskQueryWrapper);
        return ServerResponse.success(list);
    }
}
