package com.niuzhendong.graphql.common.Exception;

import lombok.Data;

@Data
public class SmsSecurityException extends BaseException {
    private static final Long serialVersionUID = 1L;
    private Integer code = 40005;

    public SmsSecurityException(Integer code, String template, Object... params) {
        super(template, params);
        this.code = code;
    }

    public SmsSecurityException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }
}
