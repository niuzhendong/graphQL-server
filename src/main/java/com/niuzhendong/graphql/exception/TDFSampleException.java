package com.niuzhendong.graphql.exception;

import com.niuzhendong.graphql.common.Exception.BaseException;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户自定义业务异常类
 */
@Data
@NoArgsConstructor
public class TDFSampleException extends BaseException {
    private static final Long serialVersionUID = 1L;
    // code为异常状态码，用户需要注意状态码的分配不重复（重复后容易产生混淆）
    // 建议使用http状态码后面补充两位数字的格式进行定义
    private static final Integer code = 41001;

    public TDFSampleException(String template, Object... params) {
        super(template, params);
    }

    public TDFSampleException(Throwable cause, String template, Object... params) {
        super(cause, template, params);
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
