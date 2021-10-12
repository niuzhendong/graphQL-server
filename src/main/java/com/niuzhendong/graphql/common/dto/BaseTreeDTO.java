package com.niuzhendong.graphql.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BaseTreeDTO<T extends BaseTreeDTO> extends BaseDTO<T>  {
    @ApiModelProperty("parent id")
    private String parentId;
    @ApiModelProperty(
            value = "树型结构DTO子对象",
            hidden = true
    )
    private List<T> children;
    @ApiModelProperty("排序字段")
    private Integer orderIndex;
    @ApiModelProperty("path")
    private String path;
}
