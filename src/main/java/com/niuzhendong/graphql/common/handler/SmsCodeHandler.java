package com.niuzhendong.graphql.common.handler;

import com.niuzhendong.graphql.common.Exception.SmsSecurityException;
import com.niuzhendong.graphql.common.dto.ResultDTO;

import java.util.Map;

public interface SmsCodeHandler {
    Boolean checkSmsCode(Map<String, Object> data) throws SmsSecurityException;

    ResultDTO sendSmsCode(String phoneNum) throws SmsSecurityException;
}
