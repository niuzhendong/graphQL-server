package com.niuzhendong.graphql.common.captcha;

import com.niuzhendong.graphql.common.dto.BaseAuthenticationRequestDTO;
import com.niuzhendong.graphql.common.dto.ResultDTO;

public interface BaseCaptchaHandler {
    ResultDTO createCaptcha(String clientIp);

    Boolean checkCaptcha(BaseAuthenticationRequestDTO authenticationRequest);
}
