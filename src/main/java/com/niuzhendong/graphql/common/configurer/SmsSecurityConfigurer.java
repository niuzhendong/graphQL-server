package com.niuzhendong.graphql.common.configurer;

import com.niuzhendong.graphql.common.filter.SmsAuthenticationFilter;
import com.niuzhendong.graphql.common.handler.DefaultSmsCodeHandle;
import com.niuzhendong.graphql.common.handler.SmsCodeHandler;
import com.niuzhendong.graphql.common.provider.SmsAuthenticationProvider;
import com.niuzhendong.graphql.common.security.BaseDefaultSecurityResponseHandler;
import com.niuzhendong.graphql.common.security.ResponseHandler;
import com.niuzhendong.graphql.common.service.SmsThirdSendService;
import com.niuzhendong.graphql.common.service.SmsUserDetailsService;
import com.niuzhendong.graphql.common.utils.CommonCacheUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

public class SmsSecurityConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<SmsSecurityConfigurer<H>, H> {
    private String enable = "on";
    private Integer maxWaitSecond = 300;
    private Integer limitPerMinutes = 1;
    private SmsUserDetailsService smsUserDetailsService;
    private SmsCodeHandler smsCodeHandler;
    private AuthenticationManager authenticationManager;
    private SmsAuthenticationFilter smsAuthenticationFilter;
    private final ApplicationContext context;
    private ResponseHandler responseHandle;

    public SmsSecurityConfigurer(ApplicationContext context) {
        this.context = context;
    }

    public void init(H builder) throws Exception {
        super.init(builder);
        this.getSmsUserDetailsService();
        this.getAuthenticationManager();
        this.getSmsCodeHandler();
        this.getResponseHandle();
    }

    public void configure(H http) {
        http.authenticationProvider(new SmsAuthenticationProvider(this.smsUserDetailsService, this.smsCodeHandler));
        this.smsAuthenticationFilter = this.getSmsAuthenticationFilter();
        this.smsAuthenticationFilter.setAuthenticationManager(this.authenticationManager);
        this.smsAuthenticationFilter.setResponseHandle(this.responseHandle);
        this.smsAuthenticationFilter.setSmsCodeHandler(this.smsCodeHandler);
        http.addFilterBefore(this.smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public SmsSecurityConfigurer<H> authenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.getSmsAuthenticationFilter();
        this.smsAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        return this;
    }

    public SmsSecurityConfigurer<H> responseHandle(ResponseHandler handle) {
        Assert.notNull(handle, "ResponseHandle cannot be null");
        this.responseHandle = handle;
        return this;
    }

    public SmsSecurityConfigurer<H> maxWaitSecond(Integer maxWaitSecond) {
        this.maxWaitSecond = maxWaitSecond;
        return this;
    }

    public SmsSecurityConfigurer<H> smsCodeHandle(SmsCodeHandler smsCodeHandler) {
        this.smsCodeHandler = smsCodeHandler;
        return this;
    }

    public SmsSecurityConfigurer<H> limitPerMinutes(Integer limitPerMinutes) {
        this.limitPerMinutes = limitPerMinutes;
        return this;
    }

    public SmsSecurityConfigurer<H> enable(String enable) {
        this.enable = enable;
        return this;
    }

    private SmsCodeHandler getSmsCodeHandler() {
        if (this.smsCodeHandler != null) {
            return this.smsCodeHandler;
        } else {
            CommonCacheUtil commonCacheUtil = (CommonCacheUtil)this.context.getBean(CommonCacheUtil.class);
            SmsThirdSendService thirdSendService = (SmsThirdSendService)this.context.getBean(SmsThirdSendService.class);
            this.smsCodeHandler = new DefaultSmsCodeHandle(this.enable, this.maxWaitSecond, this.limitPerMinutes, commonCacheUtil, thirdSendService);
            return this.smsCodeHandler;
        }
    }

    private AuthenticationManager getAuthenticationManager() {
        if (this.authenticationManager != null) {
            return this.authenticationManager;
        } else {
            AuthenticationManager authenticationManager = (AuthenticationManager)this.context.getBean(AuthenticationManager.class);
            this.authenticationManager = authenticationManager;
            return this.authenticationManager;
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

    private SmsUserDetailsService getSmsUserDetailsService() {
        if (this.smsUserDetailsService != null) {
            return this.smsUserDetailsService;
        } else {
            SmsUserDetailsService smsUserDetailsService = (SmsUserDetailsService)this.context.getBean(SmsUserDetailsService.class);
            this.smsUserDetailsService = smsUserDetailsService;
            return this.smsUserDetailsService;
        }
    }

    private SmsAuthenticationFilter getSmsAuthenticationFilter() {
        if (this.smsAuthenticationFilter != null) {
            return this.smsAuthenticationFilter;
        } else {
            this.smsAuthenticationFilter = new SmsAuthenticationFilter();
            return this.smsAuthenticationFilter;
        }
    }
}

