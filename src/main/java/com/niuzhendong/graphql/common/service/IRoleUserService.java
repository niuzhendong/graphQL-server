package com.niuzhendong.graphql.common.service;

import com.niuzhendong.graphql.common.domain.CommonDomain;
import com.niuzhendong.graphql.common.dto.RoleUserJoinDTO;

import java.util.List;

public interface IRoleUserService<D extends CommonDomain> extends IBaseJoinService<RoleUserJoinDTO, D> {
    void saveRoleUsers(String[] userIds, String preUserIds, String roleId);

    List<String> findUserIdsByRoleIds(List<String> roleIds);

    List<String> findRoleIdsByUserIds(List<String> userIds);
}
