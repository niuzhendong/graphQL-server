package com.niuzhendong.graphql.common.converter;

import com.nimbusds.jwt.JWTClaimsSet;
import com.niuzhendong.graphql.common.security.BaseAuthenticationToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaseJwtTokenAuthenticationConverter implements Converter<String, Authentication> {
    private BaseJwtTokenConverter jwtTokenConverter = new BaseJwtTokenConverter();

    public Authentication convert(String bearerToken) {
        JWTClaimsSet jwtClaimsSet = this.jwtTokenConverter.verifyRS256Token(bearerToken);
        if (jwtClaimsSet == null) {
            return null;
        } else {
            Set<GrantedAuthority> grantedAuthorities = this.obtionGrantedAuthorities(jwtClaimsSet);
            Map<String, Object> claims = jwtClaimsSet.getClaims();
            String userName = jwtClaimsSet.getSubject();
            BaseAuthenticationToken authenticationToken = new BaseAuthenticationToken(userName, claims, grantedAuthorities);
            return authenticationToken;
        }
    }

    private Set<GrantedAuthority> obtionGrantedAuthorities(JWTClaimsSet jwtClaimsSet) {
        Set<GrantedAuthority> authSet = new HashSet();
        String authorities = (String)jwtClaimsSet.getClaim("authorities");
        String[] split = authorities.split(",");
        String[] var5 = split;
        int var6 = split.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String roleId = var5[var7];
            authSet.add(new SimpleGrantedAuthority(roleId));
        }

        return authSet;
    }

    public BaseJwtTokenConverter getJwtTokenConverter() {
        return this.jwtTokenConverter;
    }

    public void setJwtTokenConverter(BaseJwtTokenConverter jwtTokenConverter) {
        this.jwtTokenConverter = jwtTokenConverter;
    }
}
