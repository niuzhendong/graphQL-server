package com.niuzhendong.graphql.common.service;

import cn.hutool.core.util.IdUtil;
import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.BaseDTO;

public interface IBaseService<DTO extends BaseDTO, D extends CommonDomain> extends IBaseCommonService<DTO, D> {
    default DTO beforeCreate(DTO dto) {
        dto = (DTO) IBaseCommonService.super.beforeCreate(dto);
        dto.setId(IdUtil.simpleUUID());
        dto.setFlag(1);
        return dto;
    }
}
