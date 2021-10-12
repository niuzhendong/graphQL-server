package com.niuzhendong.graphql.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class RoleDTO extends BaseDTO {
    private static final long serialVersionUID = 8089520763539561510L;
    @ApiModelProperty("角色描述")
    private String roleDescription;
    @ApiModelProperty("角色排序")
    private Integer roleIndex;
    @Size(
            min = 2,
            max = 200,
            message = "角色名长度只能在2-200之间"
    )
    @ApiModelProperty("角色名称")
    private String roleName;
}
