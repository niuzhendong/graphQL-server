package com.niuzhendong.graphql.common.security;

import com.niuzhendong.graphql.common.dto.CommonUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class BaseSecurityUser extends User {
    private CommonUserDTO userInfoDto;

    public BaseSecurityUser(CommonUserDTO userInfoDto, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userInfoDto = userInfoDto;
    }

    public BaseSecurityUser(CommonUserDTO userInfoDto, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userInfoDto = userInfoDto;
    }

    public CommonUserDTO getUserInfoDto() {
        return this.userInfoDto;
    }
}