package com.meng.todolist.common.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

/**
 * @ClassName ServerResponse
 * @Description Unified return template
 * @Author wangmeng
 * @Date 2022/8/13
 */
public class ServerResponse<T> implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(ServerResponse.class);

    /**
     * status code
     */
    private String code;

    /**
     * information need to be returned
     */
    private String msg;

    /**
     * data need to be returned
     */
    private T data;

    /**
     * Return success message with data
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> success(T data) {
        ServerResponse<T> serverResponseEntity = new ServerResponse<>();
        serverResponseEntity.setData(data);
        serverResponseEntity.setCode(ResponseEnum.OK.value());
        return serverResponseEntity;
    }

    /**
     * Return success message
     *
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> success() {
        ServerResponse<T> serverResponseEntity = new ServerResponse<>();
        serverResponseEntity.setCode(ResponseEnum.OK.value());
        serverResponseEntity.setMsg(ResponseEnum.OK.getMsg());
        return serverResponseEntity;
    }

    /**
     * Front end shows failure message
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> showFailMsg(String msg) {
        log.error(msg);
        ServerResponse<T> serverResponseEntity = new ServerResponse<>();
        serverResponseEntity.setMsg(msg);
        serverResponseEntity.setCode(ResponseEnum.SHOW_FAIL.value());
        return serverResponseEntity;
    }

    /**
     * Return failure message
     *
     * @param responseEnum
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> fail(ResponseEnum responseEnum) {
        log.error(responseEnum.toString());
        ServerResponse<T> serverResponseEntity = new ServerResponse<>();
        serverResponseEntity.setMsg(responseEnum.getMsg());
        serverResponseEntity.setCode(responseEnum.value());
        return serverResponseEntity;
    }

    /**
     * Transfer one type of response to another type of response
     *
     * @param oldServerResponse
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> transform(ServerResponse<?> oldServerResponse) {
        ServerResponse<T> serverResponseEntity = new ServerResponse<>();
        serverResponseEntity.setMsg(oldServerResponse.getMsg());
        serverResponseEntity.setCode(oldServerResponse.getCode());
        log.error(serverResponseEntity.toString());
        return serverResponseEntity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return Objects.equals(ResponseEnum.OK.value(), this.code);
    }

    @Override
    public String toString() {
        return "ServerResponse{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
    }

}
