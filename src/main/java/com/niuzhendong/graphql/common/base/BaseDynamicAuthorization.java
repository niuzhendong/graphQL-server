package com.niuzhendong.graphql.common.base;

import cn.hutool.core.collection.CollectionUtil;
import com.niuzhendong.graphql.common.dto.CommonUserDTO;
import com.niuzhendong.graphql.common.security.BaseSecurityUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseDynamicAuthorization {
    @Value("${base.security.access.isWhiteLogic:true}")
    public Boolean isWhiteLogic;

    public Boolean dynamicAccess(HttpServletRequest request, Authentication authentication) {
        if (!this.isAuthenticated(authentication)) {
            return false;
        } else {
            if (authentication.getPrincipal() instanceof BaseSecurityUser) {
                CommonUserDTO userInfoDto = ((BaseSecurityUser)authentication.getPrincipal()).getUserInfoDto();
                if (userInfoDto != null && "0".equals(userInfoDto.getState())) {
                    throw new AccessDeniedException("用户被锁定");
                }
            }

            Set<String> requiredAuthoritySet = this.getRequiredAuthoritySet(request);
            if (CollectionUtil.isEmpty(requiredAuthoritySet)) {
                return this.isWhiteLogic;
            } else {
                Set<String> userAuthoritySet = this.getUserAuthoritySet(authentication);
                return CollectionUtil.isNotEmpty(CollectionUtil.intersection(requiredAuthoritySet, userAuthoritySet));
            }
        }
    }

    protected Boolean isAuthenticated(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        return principal != null && !"anonymousUser".equals(principal);
    }

    protected abstract Set<String> getRequiredAuthoritySet(HttpServletRequest request);

    protected Set<String> getUserAuthoritySet(Authentication authentication) {
        Set<String> roles = (Set)authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return roles;
    }
}

