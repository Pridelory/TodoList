package com.meng.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @ClassName TodolistApplication
 * @Description Global Start Entrance
 * @Author wangmeng
 * @Date 2022/8/14
 */
@SpringBootApplication
@ComponentScan("com.meng.todolist")
@EnableOpenApi
public class TodolistApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodolistApplication.class, args);
    }

}