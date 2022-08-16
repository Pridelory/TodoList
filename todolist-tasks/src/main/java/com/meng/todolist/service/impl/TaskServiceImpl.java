package com.meng.todolist.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meng.todolist.common.constant.RedisConstants;
import com.meng.todolist.common.response.ServerResponse;
import com.meng.todolist.common.util.RedisIdWorker;
import com.meng.todolist.mapper.TaskMapper;
import com.meng.todolist.modules.tasks.models.dto.TaskDTO;
import com.meng.todolist.modules.tasks.models.entity.RedisTask;
import com.meng.todolist.modules.tasks.models.entity.Task;
import com.meng.todolist.modules.tasks.models.vo.TaskVO;
import com.meng.todolist.modules.tasks.service.ITaskService;
import com.meng.todolist.modules.user.models.bo.UserInfoBO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @ClassName TaskServiceImpl
 * @Description TaskServiceImpl
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisIdWorker redisIdWorker;

    @Override
    public ServerResponse addTaskToRedis(UserInfoBO userInfoBO, TaskDTO taskDTO) {
        RedisTask redisTask = new RedisTask();
        BeanUtils.copyProperties(taskDTO, redisTask);
        redisTask.setUserId(userInfoBO.getUserId());
        redisTask.setCreateTime(LocalDateTime.now().toString());
        redisTask.setUpdateTime(LocalDateTime.now().toString());
        redisTask.setStatus(0);
        if (redisTask.getDueTime() == null) redisTask.setDueTime(LocalDateTime.MAX.toString());
        if (redisTask.getAttachmentURL() == null) redisTask.setAttachmentURL("");

        String topicCommonKeyOfAUser = RedisConstants.TOPIC_DATA_KEY + userInfoBO.getUserId();
        // query keys with prefix "topic:data:userid"
        long taskId = redisIdWorker.nextId(String.valueOf(userInfoBO.getUserId()));
        // set the next task id in redis of a given user
        redisTask.setId(String.valueOf(taskId));
        // convert the user to the map
        Map<String, Object> taskMap = BeanUtil.beanToMap(redisTask, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        String topicDataKeyOfAUser = topicCommonKeyOfAUser + ":" + taskId;
        stringRedisTemplate.opsForHash().putAll(topicDataKeyOfAUser, taskMap);
        return ServerResponse.success(redisTask.getId());
    }


    @Override
    public ServerResponse updateTaskToRedis(UserInfoBO userInfoBO, TaskDTO taskDTO) {
        RedisTask redisTask = new RedisTask();
        BeanUtils.copyProperties(taskDTO, redisTask);
        redisTask.setId(String.valueOf(taskDTO.getId()));
        redisTask.setStatus(taskDTO.getStatusIndex());
        redisTask.setUserId(userInfoBO.getUserId());
        // TODO: 2022/8/16 update later
        redisTask.setCreateTime(LocalDateTime.now().toString());
        redisTask.setUpdateTime(LocalDateTime.now().toString());
        // TODO: 2022/8/16 update later
        if (redisTask.getDueTime() == null) redisTask.setDueTime(LocalDateTime.MAX.toString());
        if (redisTask.getAttachmentURL() == null) redisTask.setAttachmentURL("");

        String topicCommonKeyOfAUser = RedisConstants.TOPIC_DATA_KEY + userInfoBO.getUserId();
        String topicDataKeyOfAUser = topicCommonKeyOfAUser + ":" + taskDTO.getId();
        taskDTO.setId(null);
//        Map<String, Object> notNullattributePair = getAttributePair(redisTask);
//        notNullattributePair.forEach((k, v) -> stringRedisTemplate.opsForHash().put(topicDataKeyOfAUser, k, v));
        // convert the user to the map
        Map<String, Object> taskMap = BeanUtil.beanToMap(redisTask, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        // override
        stringRedisTemplate.opsForHash().putAll(topicDataKeyOfAUser, taskMap);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteTaskToRedis(UserInfoBO userInfoBO, TaskDTO taskDTO) {
        String topicCommonKeyOfAUser = RedisConstants.TOPIC_DATA_KEY + userInfoBO.getUserId();
        String topicDataKeyOfAUser = topicCommonKeyOfAUser + ":" + taskDTO.getId();
        Boolean delete = stringRedisTemplate.delete(topicDataKeyOfAUser);
        return !delete ? ServerResponse.showFailMsg("Failed to delete this task") : ServerResponse.success();
    }

    @Override
    public ServerResponse<List<TaskVO>> getTodayTaskList(UserInfoBO userInfoBO) {
        String topicCommonKeyOfAUser = RedisConstants.TOPIC_DATA_KEY + userInfoBO.getUserId();
        Set<String> keys = stringRedisTemplate.keys(topicCommonKeyOfAUser + "*");
        List<TaskVO> res = (List<TaskVO>) stringRedisTemplate.execute((RedisCallback) con -> {
            Iterator<String> it = keys.iterator();
            List<TaskVO> taskVOS = new ArrayList<>();
            while (it.hasNext()) {
                String key = it.next();
                Map<Object, Object> taskMap = stringRedisTemplate.opsForHash().entries(key);
                TaskVO taskVO = BeanUtil.fillBeanWithMap(taskMap, new TaskVO(), false);
                taskVOS.add(taskVO);
            }
            return taskVOS;
        });
        return ServerResponse.success(res);
    }

    public Map<String, Object> getAttributePair(RedisTask redisTask) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        Field[] field = redisTask.getClass().getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            String name = field[i].getName();
            String type = field[i].getGenericType().toString();
            if (type.equals("class java.lang.String")) {
                Method m;
                String value;
                try {
                    m = redisTask.getClass().getMethod("get" + name);
                    value = (String) m.invoke(redisTask);
                    if (value != null && !"".equals(value)) {
                        stringObjectHashMap.put(name, value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringObjectHashMap;
    }
}
