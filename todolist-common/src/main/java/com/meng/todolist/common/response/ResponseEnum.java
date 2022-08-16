package com.meng.todolist.common.response;

/**
 * @ClassName ResponseEnum
 * @Description A enum used to define the different types of response info
 * @Author wangmeng
 * @Date 2022/8/13
 */
public enum ResponseEnum {

    /**
     * ok
     */
    OK("00000", "ok"),

    /**
     * Used to directly display the error prompting the user, the content is determined by the input content
     */
    SHOW_FAIL("A00001", ""),

    /**
     * The method parameters are not verified, the content is determined by the input content
     */
    METHOD_ARGUMENT_NOT_VALID("A00002", ""),

    /**
     * The request parameter format is incorrect
     */
    HTTP_MESSAGE_NOT_READABLE("A00003", "The request parameter format is incorrect"),

    /**
     * unauthorized
     */
    UNAUTHORIZED("A00004", "Unauthorized"),

    /**
     * Something went wrong with the server
     */
    EXCEPTION("A00005", "Something went wrong with the server"),

    /**
     * Some interfaces that require login, but in fact,
     * because the front-end cannot know whether the token
     * has expired, and the token has expired, a status
     * code should be returned to tell the front-end
     * that the token has expired and should be cleaned up in time
     */
    CLEAN_TOKEN("A00006", "clean token"),

    /**
     * The account is not registered, the front end sees this
     * status code, a selection box pops up, prompting the user
     * account is not registered, whether to enter the registration page,
     * the user selects Yes, enter the registration page
     */
    ACCOUNT_NOT_REGISTER("A00007", "account not register");

    private final String code;

    private final String msg;

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String value() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ResponseEnum{" + "code='" + code + '\'' + ", msg='" + msg + '\'' + "} " + super.toString();
    }

}
