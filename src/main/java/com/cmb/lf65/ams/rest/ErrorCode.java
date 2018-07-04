package com.cmb.lf65.ams.rest;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public enum ErrorCode {

    UNREADABLE_REQUEST(415, "AMS-415-0001", "不支持的媒体类型"),

    JSON_SYNTAX_ERROR(400, "AMS-400-0001", "请求体存在json格式错误"),
    NON_EXIST_FIELD(400, "AMS-400-0002", "必选字段{0}不存在"),
    FIELD_VALUE_ERROR(400, "AMS-400-0003", "字段{0}的取值{1}不合法");

    private HttpStatus status;
    private String code;
    private String errorMessage;

    ErrorCode(int status, String code, String errorMessage) {
        this.status = HttpStatus.valueOf(status);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String format(String... params) {
        return MessageFormat.format(errorMessage, params);
    }
}
