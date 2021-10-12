package com.niuzhendong.graphql.common.dto;

import lombok.Data;

@Data
public class RoleMenuJoinDTO  extends BaseJoinDTO{
    private static final long serialVersionUID = -3861976282974227714L;
    private String roleId;
    private String menuId;

    public RoleMenuJoinDTO(final String roleId, final String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
