package com.niuzhendong.graphql.common.Exception;

public class CommonException extends BaseException {
    private static final Long serialVersionUID = 1L;
    private static final Integer CODE = 40003;

    public CommonException(String template, Object... params) {
        super(template, params);
    }

    public CommonException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }

    public Integer getCode() {
        return CODE;
    }
}
