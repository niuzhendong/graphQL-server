package com.niuzhendong.graphql.common.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public final class CaffeineCacheDTO implements Serializable {
    private static final long serialVersionUID = 11L;
    private Long expireSecond;
    private String value;

    public CaffeineCacheDTO(final Long expireSecond, final String value) {
        this.expireSecond = expireSecond;
        this.value = value;
    }
}
