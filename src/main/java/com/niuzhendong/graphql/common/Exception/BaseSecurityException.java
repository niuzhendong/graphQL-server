package com.niuzhendong.graphql.common.Exception;

import lombok.Data;

@Data
public class BaseSecurityException extends BaseException {
    private static final Long serialVersionUID = 1L;
    private Integer code = 40004;

    public BaseSecurityException(Integer code, String template, Object... params) {
        super(template, params);
        this.code = code;
    }

    public BaseSecurityException(String template, Object... params) {
        super(template, params);
    }

    public BaseSecurityException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }

}
