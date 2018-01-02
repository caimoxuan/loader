package com.cmx.loader.exception.enums;

public enum ExceptionEnum {

    SYSTEM_INTER_ERROR(1, "001", "系统异常", "未知的系统错误"),
    FILE_NOT_FIND(2, "002", "找不指定文件", "没有找到指定目录或文件"),
    AUTH_DENY(3, "003", "文件操作被拒绝", "没有权限访问目标文件或文件不能读写");

    private int value;
    private String errorCode;
    private String errorMessage;
    private String detail;

    ExceptionEnum(int value, String errorCode, String errorMessage, String detail) {
        this.value = value;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.detail = detail;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
