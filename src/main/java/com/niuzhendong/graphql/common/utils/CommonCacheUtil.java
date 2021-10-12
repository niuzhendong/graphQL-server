package com.niuzhendong.graphql.common.utils;

import com.niuzhendong.graphql.common.cache.ICommonCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheType;

import java.util.Set;

public class CommonCacheUtil implements ICommonCache {
    private static final Logger logger = LoggerFactory.getLogger(CommonCacheUtil.class);
    @Value("${spring.cache.type}")
    private CacheType cacheType;
    @Autowired
    private CommonRedisUtil commonRedisUtil;
    @Autowired
    private CommonCaffeineUtil commonCaffeineUtil;

    public static String getCacheKey(String dtoName, String dtoId) {
        return dtoName + ":" + dtoId;
    }

    private ICommonCache getCurrentCacheUtil() {
        return (ICommonCache)(this.cacheType.equals(CacheType.REDIS) ? this.commonRedisUtil : this.commonCaffeineUtil);
    }

    public String get(String key) {
        return this.getCurrentCacheUtil().get(key);
    }

    public void set(String key, String value) {
        this.getCurrentCacheUtil().set(key, value);
    }

    public void set(String key, String value, long expire) {
        this.getCurrentCacheUtil().set(key, value, expire);
    }

    public long increment(String key, long delta) {
        return this.getCurrentCacheUtil().increment(key, delta);
    }

    public void remove(String key) {
        this.getCurrentCacheUtil().remove(key);
    }

    public void mremove(Set<String> keys) {
        this.getCurrentCacheUtil().mremove(keys);
    }

    public void removeByPattern(String patternKey) {
        this.getCurrentCacheUtil().removeByPattern(patternKey);
    }
}
