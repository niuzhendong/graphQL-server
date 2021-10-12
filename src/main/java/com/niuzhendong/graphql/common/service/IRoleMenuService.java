package com.niuzhendong.graphql.common.service;

import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.RoleMenuJoinDTO;

import java.util.List;

public interface IRoleMenuService<D extends CommonDomain> extends IBaseJoinService<RoleMenuJoinDTO, D> {
    void saveRoleMenusForRest(String[] menuIds, List preMenuIds, String roleId);

    List<String> findRoleIdsByMenuIds(List<String> menuIds);

    List<String> findMenuIdsByRoleIds(List<String> roleIds);

    void unbindRoleMenu(String menuId, String roleId);
}
