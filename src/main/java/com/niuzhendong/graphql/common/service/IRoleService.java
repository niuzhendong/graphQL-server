package com.niuzhendong.graphql.common.service;

import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.RoleDTO;

public interface IRoleService<D extends CommonDomain> extends IBaseService<RoleDTO, D> {
    RoleDTO findByRoleName(String name);
}
