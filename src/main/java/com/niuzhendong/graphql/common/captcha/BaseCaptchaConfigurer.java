package com.niuzhendong.graphql.common.captcha;

import cn.hutool.core.collection.CollectionUtil;
import com.niuzhendong.graphql.common.security.BaseDefaultSecurityResponseHandler;
import com.niuzhendong.graphql.common.security.BaseOrRequestMatcher;
import com.niuzhendong.graphql.common.security.ResponseHandler;
import com.niuzhendong.graphql.common.utils.CommonCacheUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import java.util.List;

public class BaseCaptchaConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<BaseCaptchaConfigurer<H>, H> {
    private String captchUrl = "/captcha";
    private String captchaEnable = "on";
    private Long captchaMaxWaitSecond = 600L;
    private Long captchaSameIpLimitPerMinutes = 60L;
    private RequestMatcher createCaptchaRequestMatcher;
    private BaseOrRequestMatcher checkOrCaptchaRequestMatcher;
    private BaseCaptchaHandler baseCaptchaHandler;
    private ResponseHandler responseHandle;
    private final ApplicationContext context;

    public BaseCaptchaConfigurer(ApplicationContext context) {
        this.context = context;
    }

    public void init(H builder) throws Exception {
        super.init(builder);
        this.getCheckCaptchaRequestMatcher();
        this.getCreateCaptchaRequestMatcher();
        this.getBaseCaptchaHandler();
        this.getResponseHandle();
        this.addCheckPointRequestMatcher(new AntPathRequestMatcher("/login"));
    }

    public void configure(H http) throws Exception {
        BaseCaptchaFilter captchaFilter = new BaseCaptchaFilter();
        captchaFilter.setBaseCaptchaHandler(this.baseCaptchaHandler);
        captchaFilter.setCreateCaptchaRequestMatcher(this.createCaptchaRequestMatcher);
        captchaFilter.setHandle(this.responseHandle);
        captchaFilter.setCheckOrCaptchaRequestMatcher(this.checkOrCaptchaRequestMatcher);
        captchaFilter = (BaseCaptchaFilter)this.postProcess(captchaFilter);
        http.addFilterBefore(captchaFilter, LogoutFilter.class);
    }

    public BaseCaptchaConfigurer<H> captchaEnable(String captchaEnable) {
        this.captchaEnable = captchaEnable;
        return this;
    }

    public BaseCaptchaConfigurer<H> captchaHandler(BaseCaptchaHandler baseCaptchaHandler) {
        this.baseCaptchaHandler = baseCaptchaHandler;
        return this;
    }

    public BaseCaptchaConfigurer<H> captchaMaxWaitSecond(Long captchaMaxWaitSecond) {
        this.captchaMaxWaitSecond = captchaMaxWaitSecond;
        return this;
    }

    public BaseCaptchaConfigurer<H> responseHandle(ResponseHandler handle) {
        Assert.notNull(handle, "ResponseHandle cannot be null");
        this.responseHandle = handle;
        return this;
    }

    public BaseCaptchaConfigurer<H> captchaSameIpLimitPerMinutes(Long captchaSameIpLimitPerMinutes) {
        this.captchaSameIpLimitPerMinutes = captchaSameIpLimitPerMinutes;
        return this;
    }

    public BaseCaptchaConfigurer<H> addCheckPointRequestMatcher(RequestMatcher requestMatcher) {
        if (requestMatcher != null) {
            this.getCheckCaptchaRequestMatcher();
            this.checkOrCaptchaRequestMatcher.addRequestMatcher(requestMatcher);
        }

        return this;
    }

    public BaseCaptchaConfigurer<H> addAllCheckPointRequestMatcher(List<RequestMatcher> requestMatcherList) {
        if (CollectionUtil.isNotEmpty(requestMatcherList)) {
            this.getCheckCaptchaRequestMatcher();
            this.checkOrCaptchaRequestMatcher.addAllRequestMatcher(requestMatcherList);
        }

        return this;
    }

    private RequestMatcher getCreateCaptchaRequestMatcher() {
        if (this.createCaptchaRequestMatcher != null) {
            return this.createCaptchaRequestMatcher;
        } else {
            this.createCaptchaRequestMatcher = new AntPathRequestMatcher(this.captchUrl, "GET");
            return this.createCaptchaRequestMatcher;
        }
    }

    private RequestMatcher getCheckCaptchaRequestMatcher() {
        if (this.checkOrCaptchaRequestMatcher != null) {
            return this.checkOrCaptchaRequestMatcher;
        } else {
            this.checkOrCaptchaRequestMatcher = new BaseOrRequestMatcher();
            return this.checkOrCaptchaRequestMatcher;
        }
    }

    private BaseCaptchaHandler getBaseCaptchaHandler() {
        if (this.baseCaptchaHandler != null) {
            return this.baseCaptchaHandler;
        } else {
            CommonCacheUtil commonCacheUtil = (CommonCacheUtil)this.context.getBean("commonCacheUtil", CommonCacheUtil.class);
            this.baseCaptchaHandler = new BaseDefaultCaptchaHandler(this.captchaEnable, this.captchaMaxWaitSecond, this.captchaSameIpLimitPerMinutes, commonCacheUtil);
            return this.baseCaptchaHandler;
        }
    }

    private ResponseHandler getResponseHandle() {
        if (this.responseHandle != null) {
            return this.responseHandle;
        } else {
            this.responseHandle = new BaseDefaultSecurityResponseHandler();
            return this.responseHandle;
        }
    }
}
