package com.niuzhendong.graphql.common.configurer;

import com.niuzhendong.graphql.common.filter.BaseQrCodeFilter;
import com.niuzhendong.graphql.common.security.BaseDefaultSecurityResponseHandler;
import com.niuzhendong.graphql.common.security.ResponseHandler;
import com.niuzhendong.graphql.common.utils.CommonCacheUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

public class BaseQrCodeConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<BaseQrCodeConfigurer<H>, H> {
    private Long expiration = 120L;
    private final ApplicationContext context;
    private ResponseHandler responseHandle;

    public BaseQrCodeConfigurer(ApplicationContext context) {
        this.context = context;
    }

    public void init(H builder) throws Exception {
        super.init(builder);
        this.getResponseHandle();
    }

    public void configure(H http) throws Exception {
        BaseQrCodeFilter filter = new BaseQrCodeFilter();
        filter.setResponseHandle(this.responseHandle);
        CommonCacheUtil commonCacheUtil = (CommonCacheUtil)this.context.getBean("commonCacheUtil", CommonCacheUtil.class);
        filter.setCommonCacheUtil(commonCacheUtil);
        filter.setExpiration(this.expiration);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    public BaseQrCodeConfigurer<H> expiration(Long expiration) {
        this.expiration = expiration;
        return this;
    }

    public BaseQrCodeConfigurer<H> responseHandle(ResponseHandler handle) {
        Assert.notNull(handle, "ResponseHandle cannot be null");
        this.responseHandle = handle;
        return this;
    }

    private ResponseHandler getResponseHandle() {
        if (this.responseHandle != null) {
            return this.responseHandle;
        } else {
            this.responseHandle = new BaseDefaultSecurityResponseHandler();
            return this.responseHandle;
        }
    }
}
