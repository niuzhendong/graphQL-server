package com.niuzhendong.graphql.common.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel(
        value = "分页查询对象",
        description = "分页查询对象"
)
public class PageDTO<DTO extends CommonDTO> extends CommonPageDTO<DTO> implements Serializable {

}
