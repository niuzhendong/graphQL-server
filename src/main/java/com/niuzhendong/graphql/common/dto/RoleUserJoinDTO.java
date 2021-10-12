package com.niuzhendong.graphql.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleUserJoinDTO extends BaseJoinDTO {
    private static final long serialVersionUID = -3861976282974227713L;
    private String roleId;
    private String userId;

    public RoleUserJoinDTO(final String roleId, final String userId) {
        this.roleId = roleId;
        this.userId = userId;
    }
}