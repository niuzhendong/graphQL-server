package com.niuzhendong.graphql.common.captcha;

import cn.hutool.json.JSONUtil;
import com.niuzhendong.graphql.common.dto.BaseAuthenticationRequestDTO;
import com.niuzhendong.graphql.common.dto.ResultDTO;
import com.niuzhendong.graphql.common.security.BaseOrRequestMatcher;
import com.niuzhendong.graphql.common.security.ResponseHandler;
import com.niuzhendong.graphql.common.utils.WebSiteUtil;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseCaptchaFilter extends OncePerRequestFilter {
    private BaseCaptchaHandler baseCaptchaHandler;
    private RequestMatcher createCaptchaRequestMatcher;
    private BaseOrRequestMatcher checkOrCaptchaRequestMatcher;
    private ResponseHandler responseHandle;

    public BaseCaptchaFilter() {
    }

    public void setCreateCaptchaRequestMatcher(RequestMatcher createCaptchaRequestMatcher) {
        Assert.notNull(createCaptchaRequestMatcher, "createCaptchaRequestMatcher cannot be null");
        this.createCaptchaRequestMatcher = createCaptchaRequestMatcher;
    }

    public void setHandle(ResponseHandler handle) {
        this.responseHandle = handle;
    }

    public void setCheckOrCaptchaRequestMatcher(BaseOrRequestMatcher checkOrCaptchaRequestMatcher) {
        Assert.notNull(checkOrCaptchaRequestMatcher, "checkOrCaptchaRequestMatcher cannot be null");
        this.checkOrCaptchaRequestMatcher = checkOrCaptchaRequestMatcher;
    }

    public void setBaseCaptchaHandler(BaseCaptchaHandler baseCaptchaHandler) {
        Assert.notNull(baseCaptchaHandler, "baseCaptchaHandler cannot be null");
        this.baseCaptchaHandler = baseCaptchaHandler;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (this.requiresCreateCaptcha(request, response)) {
            String ip = WebSiteUtil.getIpAddress(request);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");

            try {
                ResultDTO dto = this.baseCaptchaHandler.createCaptcha(ip);
                response.getWriter().write(JSONUtil.toJsonStr(dto));
            } catch (Exception var6) {
                this.responseHandle.fail(request, response, var6);
            }

        } else {
            if (this.requiresCheckCaptcha(request, response)) {
                BaseAuthenticationRequestDTO authenticationRequest = new BaseAuthenticationRequestDTO(request, "username", "password");

                try {
                    this.baseCaptchaHandler.checkCaptcha(authenticationRequest);
                } catch (Exception var7) {
                    this.responseHandle.fail(request, response, var7);
                    return;
                }
            }

            chain.doFilter(request, response);
        }
    }

    protected boolean requiresCreateCaptcha(HttpServletRequest request, HttpServletResponse response) {
        if (this.createCaptchaRequestMatcher.matches(request)) {
            return true;
        } else {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("Did not match request to %s", this.createCaptchaRequestMatcher));
            }

            return false;
        }
    }

    protected boolean requiresCheckCaptcha(HttpServletRequest request, HttpServletResponse response) {
        if (this.checkOrCaptchaRequestMatcher.matches(request)) {
            return true;
        } else {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(LogMessage.format("Did not match request to %s", this.checkOrCaptchaRequestMatcher));
            }

            return false;
        }
    }
}
