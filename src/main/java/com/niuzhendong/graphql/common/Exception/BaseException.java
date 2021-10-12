package com.niuzhendong.graphql.common.Exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@NoArgsConstructor
@ResponseStatus
public abstract class BaseException extends RuntimeException {
    private static final Long serialVersionUID = 1L;
    private String message;

    public BaseException(String template, Object... params) {
        super(String.format(template, params));
        this.message = String.format(template, params);
    }

    public BaseException(Throwable cause, String template, Object... params) {
        super(String.format(template, params), cause);
        this.message = String.format(template, params);
    }

    public abstract Integer getCode();
}
