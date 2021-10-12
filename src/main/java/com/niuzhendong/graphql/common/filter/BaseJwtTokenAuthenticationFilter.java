package com.niuzhendong.graphql.common.filter;

import com.niuzhendong.graphql.common.converter.BaseJwtTokenAuthenticationConverter;
import com.niuzhendong.graphql.common.converter.BaseJwtTokenConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseJwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/refreshToken");
    private Converter<String, Authentication> converter;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (!StringUtils.startsWithIgnoreCase(tokenHeader, "base ")) {
            filterChain.doFilter(request, response);
        } else if (this.requestMatcher.matches(request)) {
            BaseJwtTokenAuthenticationConverter jwtConverter = (BaseJwtTokenAuthenticationConverter)this.converter;
            BaseJwtTokenConverter jwtTokenConverter = jwtConverter.getJwtTokenConverter();
            String token = jwtTokenConverter.refreshRS256Token(tokenHeader);
            response.setHeader("Authorization", "base " + token);
        } else {
            Authentication authentication = (Authentication)this.converter.convert(tokenHeader);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }
    }

    public void setConverter(Converter<String, Authentication> converter) {
        this.converter = converter;
    }
}
