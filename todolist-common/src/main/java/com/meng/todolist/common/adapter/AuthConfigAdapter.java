package com.meng.todolist.common.adapter;

import java.util.List;

/**
 * @ClassName AuthConfigAdapter
 * @Description Implement this interface according to business requirements to
 * achieve different interception logic, adapter design pattern
 * @Author wangmeng
 * @Date 2022/8/13
 */
public interface AuthConfigAdapter {

    /**
     * Paths that need authorization
     *
     * @return path list
     */
    List<String> pathPatterns();

    /**
     * Paths that don't need authorization
     *
     * @param paths
     * @return path list
     */
    List<String> excludePathPatterns(String... paths);

}
