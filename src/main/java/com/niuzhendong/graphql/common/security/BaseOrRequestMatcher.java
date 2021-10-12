package com.niuzhendong.graphql.common.security;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BaseOrRequestMatcher implements RequestMatcher {
    private final Log logger = LogFactory.getLog(this.getClass());
    private final List<RequestMatcher> requestMatchers = new ArrayList();

    public BaseOrRequestMatcher() {
    }

    public boolean matches(HttpServletRequest request) {
        Iterator var2 = this.requestMatchers.iterator();

        RequestMatcher matcher;
        do {
            if (!var2.hasNext()) {
                this.logger.debug("No matches found");
                return false;
            }

            matcher = (RequestMatcher)var2.next();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Trying to match using " + matcher);
            }
        } while(!matcher.matches(request));

        this.logger.debug("matched");
        return true;
    }

    public String toString() {
        return "BaseOrRequestMatcher [requestMatchers=" + this.requestMatchers + "]";
    }

    public void addRequestMatcher(RequestMatcher requestMatcher) {
        if (requestMatcher != null) {
            this.requestMatchers.add(requestMatcher);
        }

    }

    public void addAllRequestMatcher(List<RequestMatcher> requestMatcherList) {
        if (CollectionUtil.isNotEmpty(requestMatcherList)) {
            this.requestMatchers.addAll(requestMatcherList);
        }

    }
}
