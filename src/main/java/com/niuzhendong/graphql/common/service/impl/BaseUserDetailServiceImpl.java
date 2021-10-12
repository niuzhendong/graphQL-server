package com.niuzhendong.graphql.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.niuzhendong.graphql.common.dto.CommonUserDTO;
import com.niuzhendong.graphql.common.security.BaseSecurityUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseUserDetailServiceImpl implements UserDetailsService {

    public abstract CommonUserDTO findCommonUserDTOByLoginName(String loginName);

    public abstract List<String> findRoleIdsByLoginName(String loginName);

    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        CommonUserDTO user = this.findCommonUserDTOByLoginName(loginName);
        if (user == null) {
            throw new UsernameNotFoundException(loginName);
        } else if (!"1".equals(user.getState())) {
            throw new UsernameNotFoundException("该用户处于锁定状态");
        } else {
            Collection<GrantedAuthority> grantedAuths = this.obtionGrantedAuthorities(loginName);
            boolean enables = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;
            BaseSecurityUser userdetail = new BaseSecurityUser(user, user.getLoginName(), user.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
            return userdetail;
        }
    }

    private Set<GrantedAuthority> obtionGrantedAuthorities(String loginName) {
        Set<GrantedAuthority> authSet = new HashSet();
        List<String> roleIds = this.findRoleIdsByLoginName(loginName);
        if (CollectionUtil.isNotEmpty(roleIds)) {
            roleIds.forEach((roleId) -> {
                authSet.add(new SimpleGrantedAuthority(roleId));
            });
        }

        return authSet;
    }
}

