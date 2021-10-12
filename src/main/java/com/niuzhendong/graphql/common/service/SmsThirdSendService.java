package com.niuzhendong.graphql.common.service;

import java.util.Map;

public interface SmsThirdSendService {
    Boolean send(Map<String, Object> data);
}
