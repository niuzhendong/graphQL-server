package com.niuzhendong.graphql.common.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
@Data
public class ResultDTO<T> implements Serializable {
    private static final long serialVersionUID = 2783377098145240357L;
    private Integer code;
    private String message;
    private T data;

    public ResultDTO(T data) {
        this.code = HttpStatus.OK.value();
        this.message = "操作成功";
        this.data = data;
    }

    public ResultDTO(Integer code) {
        this.code = HttpStatus.OK.value();
        this.message = "操作成功";
        this.code = code;
    }

    public ResultDTO(Integer code, T data) {
        this.code = HttpStatus.OK.value();
        this.message = "操作成功";
        this.code = code;
        this.data = data;
    }

    public ResultDTO() {
        this.code = HttpStatus.OK.value();
        this.message = "操作成功";
    }

    public ResultDTO(final Integer code, final String message, final T data) {
        this.code = HttpStatus.OK.value();
        this.message = "操作成功";
        this.code = code;
        this.message = message;
        this.data = data;
    }
}