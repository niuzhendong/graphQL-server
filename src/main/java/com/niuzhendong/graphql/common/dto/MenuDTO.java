package com.niuzhendong.graphql.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class MenuDTO  extends BaseTreeDTO {
    private static final long serialVersionUID = -28594460214454L;
    @ApiModelProperty("暂时没用")
    private String controllerClass;
    @ApiModelProperty("图标路径")
    private String iconPath;
    @ApiModelProperty("菜单描述")
    private String menuDesc;
    @ApiModelProperty("菜单排序号")
    private Integer menuIndex;
    @NotBlank(
            message = "菜单名称不能为空"
    )
    @ApiModelProperty("菜单名称")
    private String menuName;
    @ApiModelProperty("菜单路径")
    private String menuUrl;
    @ApiModelProperty("菜单备注")
    private String mark;
    @ApiModelProperty("小图标途径")
    private String smallIconPath;
    @ApiModelProperty("按钮权限ID")
    private String authorityId;
    @ApiModelProperty("菜单类型")
    private String menuClassify;
}
