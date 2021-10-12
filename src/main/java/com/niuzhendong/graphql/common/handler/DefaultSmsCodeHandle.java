package com.niuzhendong.graphql.common.handler;

import cn.hutool.core.util.StrUtil;
import com.niuzhendong.graphql.common.Exception.SmsSecurityException;
import com.niuzhendong.graphql.common.dto.ResultDTO;
import com.niuzhendong.graphql.common.service.SmsThirdSendService;
import com.niuzhendong.graphql.common.utils.CommonBeanUtil;
import com.niuzhendong.graphql.common.utils.CommonCacheUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class DefaultSmsCodeHandle implements SmsCodeHandler {
    private static final Log logger = LogFactory.getLog(DefaultSmsCodeHandle.class);
    private static final String PHONE_CODE_CACHE_KEY_PRE = "base:phoneCode";
    private static final String PHONE_CODE_SAME_LIMIT_CACHE_KEY_PRE = "base:phoneLimitPerMinutes";
    private String enable = "on";
    private Integer maxWaitSecond = 300;
    private Integer limitPerMinutes;
    private CommonCacheUtil commonCacheUtil;
    private SmsThirdSendService thirdSendService;

    public DefaultSmsCodeHandle(String enable, Integer maxWaitSecond, Integer limitPerMinutes, CommonCacheUtil commonCacheUtil, SmsThirdSendService thirdSendService) {
        this.enable = enable;
        this.maxWaitSecond = maxWaitSecond;
        this.limitPerMinutes = limitPerMinutes;
        this.commonCacheUtil = commonCacheUtil;
        this.thirdSendService = thirdSendService;
    }

    public Boolean checkSmsCode(Map<String, Object> data) throws SmsSecurityException {
        logger.error("开始检查短信验证码");
        if (this.enable.equals("on")) {
            String phoneCode = (String)data.get("phoneCode");
            String phoneCodeKey = (String)data.get("phoneCodeKey");
            if (StrUtil.isBlank(phoneCode)) {
                throw new SmsSecurityException(5002, "手机校验码Key不可以为空", new Object[0]);
            }

            String storedCaptchaCode = this.commonCacheUtil.get("base:phoneCode:" + phoneCodeKey);
            if (StrUtil.isBlank(storedCaptchaCode) || !storedCaptchaCode.equals(phoneCode)) {
                throw new SmsSecurityException(5003, "手机校验码错误或已过期，请重新获取手机校验码", new Object[0]);
            }
        }

        return true;
    }

    public ResultDTO sendSmsCode(String phoneNumber) throws SmsSecurityException {
        if (StrUtil.isBlank(phoneNumber)) {
            throw new SmsSecurityException(5004, "手机号不能为空", new Object[0]);
        } else {
            String limitKey = "base:phoneCode:" + phoneNumber;
            String time = this.commonCacheUtil.get(limitKey);
            if (StrUtil.isBlank(time)) {
                time = this.limitPerMinutes + "";
                this.commonCacheUtil.set(limitKey, time, 60L);
            }

            Integer t = Integer.parseInt(time);
            if (t <= 0 && this.enable.equals("on")) {
                throw new SmsSecurityException(5005, "同一个手机号每分钟发送" + this.limitPerMinutes + "次短信", new Object[0]);
            } else {
                String phoneCode = (int)((Math.random() * 9.0D + 1.0D) * 1000.0D) + "";
                String phoneCodeKey = CommonBeanUtil.generateBeanId();
                this.commonCacheUtil.set("base:phoneCode:" + phoneCodeKey, phoneCode, (long)this.maxWaitSecond);
                Map<String, Object> result = new HashMap();
                result.put("phoneCodeKey", phoneCodeKey);
                if (this.enable.equals("on")) {
                    result.put("phoneCode", phoneCode);
                }

                this.thirdSendService.send(result);
                this.commonCacheUtil.increment(limitKey, -1L);
                return new ResultDTO(result);
            }
        }
    }
}
