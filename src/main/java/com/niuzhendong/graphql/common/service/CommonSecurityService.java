package com.niuzhendong.graphql.common.service;

import java.util.Set;

public abstract class CommonSecurityService {
    public static CommonSecurityService instance;

    public CommonSecurityService() {
        instance = this;
    }

    public abstract String getCurrentLoginName();

    public abstract Set<String> getCurrentAuthorities();

    public abstract String encodePassword(String rawPassword);
}
