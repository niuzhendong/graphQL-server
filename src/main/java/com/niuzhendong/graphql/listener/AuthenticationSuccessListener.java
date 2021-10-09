package com.niuzhendong.graphql.listener;

import com.sample.tdf.dto.LoginLogDTO;
import com.sample.tdf.service.ILoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * 用户登录成功监听器
 *
 * @author arjun
 * @date 2020/12/08
 */
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private ILoginLogService iLoginLogService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        String name = authentication.getName();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();
        LoginLogDTO dto = new LoginLogDTO();
        dto.setMessage("登陆成功");
        dto.setResult(1);
        dto.setUsername(name);
        dto.setRemoteAddress(remoteAddress);
        iLoginLogService.create(dto);
    }

}
