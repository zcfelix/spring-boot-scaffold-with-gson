package com.cmb.lf65.ams.domain;

import com.cmb.lf65.ams.rest.ErrorCode;

/**用于表示统一的错误请求对象
 * @author zhouying
 */
public class Error {

    /**
     * 适用于这个错误的HTTP状态码
     */
    private String status;

    /**
     * 应用特定的错误码，按需进行定义
     */
    private String code;

    /**
     * 简短的，可读性高的错误总结
     */
    private String title;

    /**
     * 针对该错误的详细解释
     */
    private String detail;

    /**
     * {@link Error#status}
     */
    public String getStatus() {
        return status;
    }

    /**
     * {@link Error#code}
     */
    public String getCode() {
        return code;
    }

    /**
     * {@link Error#title}
     */
    public String getTitle() {
        return title;
    }


    /**
     * {@link Error#detail}
     */
    public String getDetail() {
        return detail;
    }

    private Error() {
    }

    public static Error fromErrorCode(ErrorCode errorCode, String... params) {
        final Error error = new Error();
        error.status = errorCode.getStatus().toString();
        error.code = errorCode.getCode();
        error.title = errorCode.format(params);
        error.detail = errorCode.format(params);
        return error;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Error error = new Error();

        public Builder withStatus(String status) {
            error.status = status;
            return this;
        }

        public Builder withCode(String code) {
            error.code = code;
            return this;
        }

        public Builder withTitle(String title) {
            error.title = title;
            return this;
        }

        public Builder withDetail(String detail) {
            error.detail = detail;
            return this;
        }

        public Error build() {
            return error;
        }
    }
}
