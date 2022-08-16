package com.meng.todolist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.todolist.modules.tasks.models.entity.Task;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName TaskMapper
 * @Description TaskMapper
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {
}
