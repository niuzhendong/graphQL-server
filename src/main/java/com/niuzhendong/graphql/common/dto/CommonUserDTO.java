package com.niuzhendong.graphql.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public abstract class CommonUserDTO extends BaseDTO {
    private static final long serialVersionUID = 11718455276020707L;
    @ApiModelProperty("登录名")
    private String loginName;
    private String password;
    @ApiModelProperty("真实姓名")
    private String userName;
    @ApiModelProperty("手机号码")
    @Pattern(
            regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",
            message = "手机号格式不正确"
    )
    private String phoneNum;
    @ApiModelProperty(
            value = "是否启用（锁定），1是启用（不锁定），0是不启用（被锁定）",
            example = "1"
    )
    private String state;

}