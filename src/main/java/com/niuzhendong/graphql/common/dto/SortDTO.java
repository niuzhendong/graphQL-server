package com.niuzhendong.graphql.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(
        value = "分页查询排序字段对象",
        description = "分页查询排序字段对象"
)
@Data
public final class SortDTO implements Serializable {
    private static final long serialVersionUID = 11L;
    @ApiModelProperty("需要排序的属性名称")
    private String fieldName;
    @ApiModelProperty(
            value = "ASC或者DESC",
            example = "asc"
    )
    private String direction;

}
