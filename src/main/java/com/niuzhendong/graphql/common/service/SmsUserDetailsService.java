package com.niuzhendong.graphql.common.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface SmsUserDetailsService {
    UserDetails loadByPhoneNum(String phoneNum);
}
