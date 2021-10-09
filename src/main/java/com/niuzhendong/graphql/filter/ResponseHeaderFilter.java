package com.niuzhendong.graphql.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于统一添加响应头,解决渗透测试时报的漏洞
 */
@Component
public class ResponseHeaderFilter extends OncePerRequestFilter {
 
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse res,
                                    FilterChain filterChain) throws ServletException, IOException {
        res.addHeader("X-Download-Options","noopen");
        res.addHeader("X-Content-Type-Options","nosniff");
        res.addHeader("X-XSS-Protection","1; mode=block");
        res.addHeader("X-Permitted-Cross-Domain-Policies","master-only");
        res.addHeader("Referer-Policy","origin");
        res.addHeader("X-Frame-Options","SAMEORIGIN");
        res.addHeader("Content-Security-Policy","object-src 'self'");
        filterChain.doFilter(httpServletRequest, res);
    }
}
