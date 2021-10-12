package com.niuzhendong.graphql.common.captcha;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.niuzhendong.graphql.common.Exception.BaseSecurityException;
import com.niuzhendong.graphql.common.dto.BaseAuthenticationRequestDTO;
import com.niuzhendong.graphql.common.dto.ResultDTO;
import com.niuzhendong.graphql.common.utils.CommonBeanUtil;
import com.niuzhendong.graphql.common.utils.CommonCacheUtil;

public class BaseDefaultCaptchaHandler implements BaseCaptchaHandler {
    private static final String captchaEnableOn = "on";
    private static final String captchaRedisKeyPre = "base:captcha";
    private static final String captchaSameIpLimitRedisKeyPre = "base:ipLimitPerMinutes";
    private String captchaEnable;
    private Long captchaMaxWaitSecond;
    private Long captchaSameIpLimitPerMinutes;
    private CommonCacheUtil commonCacheUtil;

    public BaseDefaultCaptchaHandler(String captchaEnable, Long captchaMaxWaitSecond, Long captchaSameIpLimitPerMinutes, CommonCacheUtil commonCacheUtil) {
        this.captchaEnable = captchaEnable;
        this.captchaMaxWaitSecond = captchaMaxWaitSecond;
        this.captchaSameIpLimitPerMinutes = captchaSameIpLimitPerMinutes;
        this.commonCacheUtil = commonCacheUtil;
    }

    public ResultDTO createCaptcha(String clientIp) {
        String ipLimitRedisKey = "base:ipLimitPerMinutes:" + clientIp;
        String time = this.commonCacheUtil.get(ipLimitRedisKey);
        if (StrUtil.isBlank(time)) {
            time = this.captchaSameIpLimitPerMinutes + "";
            this.commonCacheUtil.set(ipLimitRedisKey, time, 60L);
        }

        Integer t = Integer.parseInt(time);
        if (t <= 0) {
            throw new BaseSecurityException("每分钟只能获取" + this.captchaSameIpLimitPerMinutes + "次验证码", new Object[0]);
        } else {
            String captchaCode = (int)((Math.random() * 9.0D + 1.0D) * 1000.0D) + "";
            String key = CommonBeanUtil.generateBeanId();
            this.commonCacheUtil.set("base:captcha:" + key, captchaCode, this.captchaMaxWaitSecond);
            this.commonCacheUtil.increment(ipLimitRedisKey, -1L);
            return new ResultDTO(MapUtil.builder().put("captchaKey", key).put("captchaCode", captchaCode).build());
        }
    }

    public Boolean checkCaptcha(BaseAuthenticationRequestDTO authenticationRequest) {
        if ("on".equals(this.captchaEnable)) {
            String captchaKey = authenticationRequest.getCaptchaKey();
            String captchaCode = authenticationRequest.getCaptchaCode();
            if (StrUtil.isBlank(captchaKey)) {
                throw new BaseSecurityException("验证码Key不可以为空", new Object[0]);
            }

            String storedCaptchaCode = this.commonCacheUtil.get("base:captcha:" + captchaKey);
            if (StrUtil.isBlank(storedCaptchaCode) || !storedCaptchaCode.equals(captchaCode)) {
                throw new BaseSecurityException("验证码错误或已过期，请刷新验证码", new Object[0]);
            }
        }

        return true;
    }
}
