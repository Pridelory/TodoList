package com.meng.todolist.common.exception;

import com.meng.todolist.common.response.ResponseEnum;

/**
 * @ClassName TodolistException
 * @Description Encapulate the exception
 * @Author wangmeng
 * @Date 2022/8/13
 */
public class TodolistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Object object;

	private ResponseEnum responseEnum;

	public TodolistException(String msg) {
		super(msg);
	}

	public TodolistException(String msg, Object object) {
		super(msg);
		this.object = object;
	}

	public TodolistException(String msg, Throwable cause) {
		super(msg, cause);
	}


	public TodolistException(ResponseEnum responseEnum) {
		super(responseEnum.getMsg());
		this.responseEnum = responseEnum;
	}

	public TodolistException(ResponseEnum responseEnum, Object object) {
		super(responseEnum.getMsg());
		this.responseEnum = responseEnum;
		this.object = object;
	}


	public Object getObject() {
		return object;
	}

	public ResponseEnum getResponseEnum() {
		return responseEnum;
	}

}
