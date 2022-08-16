package com.meng.todolist.config;

import cn.hutool.core.util.ArrayUtil;
import com.meng.todolist.common.adapter.AuthConfigAdapter;
import com.meng.todolist.common.adapter.DefaultAuthConfigAdapter;
import com.meng.todolist.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.servlet.DispatcherType;

/**
 * @ClassName AuthConfig
 * @Description Authorization Configuration
 * @Author wangmeng
 * @Date 2022/8/14
 */
@Configuration
public class AuthConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    @ConditionalOnMissingBean
    public AuthConfigAdapter authConfigAdapter() {
        return new DefaultAuthConfigAdapter();
    }

    @Bean
    @Lazy
    public FilterRegistrationBean<AuthFilter> filterRegistration(AuthConfigAdapter authConfigAdapter) {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        // add filter
        registration.setFilter(authFilter);
        // set the filter path
        registration.addUrlPatterns(ArrayUtil.toArray(authConfigAdapter.pathPatterns(), String.class));
        registration.setName("authFilter");
        // set the priority
        registration.setOrder(0);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }

}
