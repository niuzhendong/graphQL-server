package com.niuzhendong.graphql.listener;

import com.sample.tdf.dto.LoginLogDTO;
import com.sample.tdf.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * 用户登录失败监听器
 *
 * @author arjun
 * @date 2020/12/08
 */
@Component
class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    @Autowired
    private ILoginLogService iLoginLogService;

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        AuthenticationException exception = event.getException();
        Authentication authentication = event.getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();
        String name = authentication.getName();
        LoginLogDTO dto = new LoginLogDTO();
        dto.setMessage(exception.getMessage());
        dto.setResult(0);
        dto.setUsername(name);
        dto.setRemoteAddress(remoteAddress);
        iLoginLogService.create(dto);
    }
}
