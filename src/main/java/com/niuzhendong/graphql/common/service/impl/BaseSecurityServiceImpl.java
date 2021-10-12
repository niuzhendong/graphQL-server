package com.niuzhendong.graphql.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.niuzhendong.graphql.common.Exception.BaseSecurityException;
import com.niuzhendong.graphql.common.complexity.BasePasswordComplexity;
import com.niuzhendong.graphql.common.complexity.BasePasswordPattern;
import com.niuzhendong.graphql.common.service.CommonSecurityService;
import com.niuzhendong.graphql.common.security.BaseAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class BaseSecurityServiceImpl extends CommonSecurityService {
    private Logger logger = LoggerFactory.getLogger(BaseSecurityServiceImpl.class);
    private PasswordEncoder passwordEncoder;
    private BasePasswordComplexity basePasswordComplexity;

    public BaseSecurityServiceImpl(PasswordEncoder passwordEncoder, BasePasswordComplexity basePasswordComplexity) {
        this.passwordEncoder = passwordEncoder;
        this.basePasswordComplexity = basePasswordComplexity;
    }

    public String getCurrentLoginName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String loginName = null;
        if (authentication == null) {
            this.logger.debug("anonymousUser");
            return "anonymousUser";
        } else {
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails)authentication.getPrincipal();
                    loginName = userDetails.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    loginName = (String)authentication.getPrincipal();
                } else if (authentication instanceof AbstractAuthenticationToken) {
                    loginName = authentication.getName();
                }
            } else if (authentication instanceof BaseAuthenticationToken) {
                this.logger.debug("authentication is BaseAuthenticationToken");
                loginName = (String)authentication.getPrincipal();
            } else {
                loginName = authentication.getName();
            }

            return loginName;
        }
    }

    public Set<String> getCurrentAuthorities() {
        Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Set<String> cs = new HashSet();
        if (CollectionUtil.isNotEmpty(grantedAuthorities)) {
            grantedAuthorities.forEach((g) -> {
                cs.add(g.getAuthority());
            });
        }

        return cs;
    }

    public String encodePassword(String rawPassword) {
        String name = this.basePasswordComplexity.getName();
        Boolean enable = this.basePasswordComplexity.getEnable();
        if (enable) {
            BasePasswordPattern basePasswordPattern = (BasePasswordPattern)this.basePasswordComplexity.getPatterns().get(name);
            boolean matches = Pattern.matches(basePasswordPattern.getPattern(), rawPassword);
            if (!matches) {
                throw new BaseSecurityException(basePasswordPattern.getMessage(), new Object[0]);
            }
        }

        return this.passwordEncoder.encode(rawPassword);
    }
}
