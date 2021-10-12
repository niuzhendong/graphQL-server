package com.niuzhendong.graphql.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseDTO<T extends BaseDTO> extends BaseCommonDTO {
    @ApiModelProperty(
            value = "创建人",
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    protected String createdBy;

    @ApiModelProperty(
            value = "创建时间",
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    protected LocalDateTime createdDate;

    @ApiModelProperty(
            value = "修改人",
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    protected String modifiedBy;

    @ApiModelProperty(
            value = "修改时间",
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    protected LocalDateTime modifiedDate;

    @ApiModelProperty(
            value = "逻辑删除标志。0是已删除",
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    private Integer flag;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty(
            value = "乐观锁2",
            hidden = true,
            accessMode = ApiModelProperty.AccessMode.READ_ONLY
    )
    private String token;
}
