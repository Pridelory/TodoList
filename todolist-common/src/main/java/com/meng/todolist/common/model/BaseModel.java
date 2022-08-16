package com.meng.todolist.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName BaseModel
 * @Description The father object of the entity class
 * @Author wangmeng
 * @Date 2022/8/13
 */
@Data
public class BaseModel implements Serializable {

    /**
     * create time
     */
    protected Date createTime;

    /**
     * update time
     */
    protected Date updateTime;

}
