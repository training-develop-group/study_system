package com.example.study_system.emun;

/**
 * author lindan.
 * date 2019/6/4.
 */
public enum ResultEmun {
    FAIL(-1,"操作失败"),
    SUCCESS(1, "操作成功"),
    ALL_DATA(2, "返回全部数据"),
    NO_DATA(3, "没有数据"),
    INTERNAL_SERVER_ERROR(-1, "系统开小差啦~ 请重试"),//系统出小差
    VALIDATION_ERROR(-2, "参数错误"),//参数错误，拒绝访问
    UNAUTHORIZED_ERROR(-3, "获取用户失败，请重试或重新登录"),
    FORBIDDEN_ERROR(-4, "没有权限"),
    VALIDATION_FORAMT_ERROR(-5, "数据格式错误"),//参数错误，拒绝访问
    METHOD_NOT_ALLOWED(-6, "Method not allowed"),//参数异常
	UPLOAD_FILE_ISEMPTY(-7, "上传文件数据为空"),
	THIS_FILE_FORMAT_IS_NOT_SUPPORTED(-8, "不支持此文件格式");
    private Integer code;
    private String message;

    ResultEmun(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
