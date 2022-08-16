package com.meng.todolist.modules.user.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.meng.todolist.common.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description User
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Data
public class User extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Email
     */
    private String email;

    /**
     * Password
     */
    private String password;

    /**
     * status 1 normal 0 invalied
     */
    private Integer status;
}
