package com.niuzhendong.graphql.common.filter;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.niuzhendong.graphql.common.Exception.BaseSecurityException;
import com.niuzhendong.graphql.common.dto.ResultDTO;
import com.niuzhendong.graphql.common.security.ResponseHandler;
import com.niuzhendong.graphql.common.utils.CommonCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseQrCodeFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(BaseQrCodeFilter.class);
    private RequestMatcher idRequestMatcher = new AntPathRequestMatcher("/qrcodeId");
    private RequestMatcher authRequestMatcher = new AntPathRequestMatcher("/qrcodeAuth");
    private RequestMatcher tryRequestMatcher = new AntPathRequestMatcher("/qrcodeTryAuth");
    private CommonCacheUtil commonCacheUtil;
    private ResponseHandler responseHandle;
    private Long expiration;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String qrcodeId;
        if (this.idRequestMatcher.matches(request)) {
            qrcodeId = IdUtil.randomUUID();
            this.commonCacheUtil.set(this.getQRCodeRedisKey(qrcodeId), "false", this.expiration);
            this.responseHandle.success(request, response, new ResultDTO(qrcodeId));
        } else if (this.authRequestMatcher.matches(request)) {
            qrcodeId = request.getParameter("qrcodeId");
            if (StrUtil.isBlank(qrcodeId)) {
                this.responseHandle.fail(request, response, new BaseSecurityException(401, "qrcodeId不能空", new Object[0]));
            }

            this.commonCacheUtil.set(this.getQRCodeRedisKey(qrcodeId), "true", this.expiration);
            this.responseHandle.success(request, response, new ResultDTO("扫描成功，允许登录"));
        } else if (this.tryRequestMatcher.matches(request)) {
            qrcodeId = request.getParameter("qrcodeId");
            if (StrUtil.isBlank(qrcodeId)) {
                this.responseHandle.fail(request, response, new BaseSecurityException(401, "qrcodeId不能空", new Object[0]));
            }

            String cache = this.commonCacheUtil.get(this.getQRCodeRedisKey(qrcodeId));
            if (StrUtil.isBlank(cache)) {
                this.responseHandle.success(request, response, new ResultDTO("0:二维码已经过期"));
            } else if ("false".equals(cache)) {
                this.responseHandle.success(request, response, new ResultDTO("1:手机端未允许登录，请等待手机端确认或者刷新二维码"));
            } else {
                String token = request.getParameter("token");
                if (StrUtil.isBlank(token)) {
                    token = request.getHeader("Authorization");
                }

                this.responseHandle.success(request, response, new ResultDTO("200:" + token));
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public CommonCacheUtil getCommonCacheUtil() {
        return this.commonCacheUtil;
    }

    public void setCommonCacheUtil(CommonCacheUtil commonCacheUtil) {
        this.commonCacheUtil = commonCacheUtil;
    }

    public ResponseHandler getResponseHandle() {
        return this.responseHandle;
    }

    public void setResponseHandle(ResponseHandler responseHandle) {
        this.responseHandle = responseHandle;
    }

    public Long getExpiration() {
        return this.expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    private String getQRCodeRedisKey(String qrcodeId) {
        return "qrcode::" + qrcodeId;
    }
}

