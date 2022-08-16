package com.meng.todolist.common.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName DefaultAuthConfigAdapter
 * @Description Implementation adapter of AuthConfigAdapter according to default scenario
 * @Author wangmeng
 * @Date 2022/8/13
 */
public class DefaultAuthConfigAdapter implements AuthConfigAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthConfigAdapter.class);
    /**
     * No login permission required
     */
    private static final String REGISTER_URI = "/api/user/register";
    /**
     * No login permission required
     */
    private static final String LOGIN_URI = "/api/user/login";

    /**
     * No login permission required
     * swagger documents
     */
    private static final String SWAGGER_URI = "/swagger**/**";


    public DefaultAuthConfigAdapter() {
    }

    @Override
    public List<String> pathPatterns() {
        return Collections.singletonList("/*");
    }

    @Override
    public List<String> excludePathPatterns(String... paths) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(LOGIN_URI);
        arrayList.add(REGISTER_URI);
        arrayList.add(SWAGGER_URI);
        arrayList.add("/webjars/**");
        arrayList.add("/v3/**");
        arrayList.add("/doc.html");
        arrayList.addAll(Arrays.asList(paths));
        return arrayList;
    }
}
