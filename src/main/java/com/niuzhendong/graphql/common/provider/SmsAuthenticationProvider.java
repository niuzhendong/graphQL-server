package com.niuzhendong.graphql.common.provider;

import com.niuzhendong.graphql.common.Exception.SmsSecurityException;
import com.niuzhendong.graphql.common.handler.SmsCodeHandler;
import com.niuzhendong.graphql.common.security.BaseAuthenticationToken;
import com.niuzhendong.graphql.common.service.SmsUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public class SmsAuthenticationProvider implements AuthenticationProvider {
    private static final Log logger = LogFactory.getLog(SmsAuthenticationProvider.class);
    private SmsUserDetailsService smsUserDetailsService;
    private SmsCodeHandler smsCodeHandler;

    public SmsAuthenticationProvider(SmsUserDetailsService smsUserDetailsService, SmsCodeHandler smsCodeService) {
        this.smsUserDetailsService = smsUserDetailsService;
        this.smsCodeHandler = smsCodeService;
    }

    public Authentication authenticate(Authentication authentication) throws SmsSecurityException {
        String phoneNum = (String)authentication.getPrincipal();
        Map<String, Object> credentials = (Map)authentication.getCredentials();
        this.smsCodeHandler.checkSmsCode(credentials);
        UserDetails userDetails = this.smsUserDetailsService.loadByPhoneNum(phoneNum);
        BaseAuthenticationToken authenticationToken = new BaseAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
        return authenticationToken;
    }

    public boolean supports(Class<?> authentication) {
        return BaseAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
