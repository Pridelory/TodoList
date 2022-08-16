package com.meng.todolist.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.meng.todolist.modules.user.models.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName AuthenticationMapper
 * @Description AuthenticationMapper
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Mapper
public interface AuthenticationMapper extends BaseMapper<User> {

}
