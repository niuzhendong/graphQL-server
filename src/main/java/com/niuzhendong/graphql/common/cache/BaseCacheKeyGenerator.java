package com.niuzhendong.graphql.common.cache;

import com.niuzhendong.graphql.common.dto.BaseDTO;
import com.niuzhendong.graphql.common.dto.BaseJoinDTO;
import com.niuzhendong.graphql.common.service.ICommonService;
import com.niuzhendong.graphql.common.utils.CommonCacheUtil;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class BaseCacheKeyGenerator implements KeyGenerator {
    public BaseCacheKeyGenerator() {
    }

    public Object generate(Object target, Method method, Object... params) {
        String dtoClassName = ((ICommonService)target).getDTOClass().getSimpleName();
        String id = "";
        if (params[0] instanceof BaseDTO) {
            id = ((BaseDTO)params[0]).getId();
        }

        if (params[0] instanceof BaseJoinDTO) {
            id = ((BaseJoinDTO)params[0]).getId();
        } else if (params[0] instanceof String) {
            id = (String)params[0];
        }

        return CommonCacheUtil.getCacheKey(dtoClassName, id);
    }
}
