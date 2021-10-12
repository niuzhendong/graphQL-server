package com.niuzhendong.graphql.common.utils;

import com.niuzhendong.graphql.common.Exception.CommonException;
import com.niuzhendong.graphql.common.cache.ICommonCache;
import com.niuzhendong.graphql.common.dto.CaffeineCacheDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CommonCaffeineUtil implements ICommonCache {
    private static final Logger logger = LoggerFactory.getLogger(CommonCaffeineUtil.class);
    private Cache<String, CaffeineCacheDTO> caffeineCache = Caffeine.newBuilder().recordStats().expireAfter(new Expiry<String, CaffeineCacheDTO>() {
        public long expireAfterCreate(String key, CaffeineCacheDTO dto, long currentTime) {
            return TimeUnit.NANOSECONDS.convert(dto.getExpireSecond(), TimeUnit.SECONDS);
        }

        public long expireAfterUpdate(String key, CaffeineCacheDTO dto, long currentTime, long currentDuration) {
            return currentDuration;
        }

        public long expireAfterRead(String key, CaffeineCacheDTO dto, long currentTime, long currentDuration) {
            return currentDuration;
        }
    }).build();

    public String get(String key) {
        logger.debug("Enter get() key={}", key);
        CaffeineCacheDTO dto = (CaffeineCacheDTO)this.caffeineCache.getIfPresent(key);
        return dto == null ? null : dto.getValue();
    }

    public void set(String key, String value) {
        logger.debug("Enter set() key={}, value={}", key, value);
        this.caffeineCache.put(key, new CaffeineCacheDTO(9223372036854775807L, value));
    }

    public void set(String key, String value, long expire) {
        logger.debug("Enter set() key={}, value={}", key, value);
        this.caffeineCache.put(key, new CaffeineCacheDTO(expire, value));
    }

    public long increment(String key, long delta) {
        Long value = Long.valueOf(this.get(key)) + delta;
        this.set(key, String.valueOf(value));
        return value;
    }

    public void remove(String key) {
        logger.debug("Enter remove() key={}", key);
        this.caffeineCache.invalidate(key);
    }

    public void mremove(Set<String> keys) {
        logger.debug("Enter mremove() keys={}", keys);
        this.caffeineCache.invalidateAll(keys);
    }

    public void removeByPattern(String patternKey) {
        logger.debug("Enter remove() key={}", patternKey);
        if (!patternKey.contains("*")) {
            throw new CommonException("Caffenion目前不支持此patter匹配", new Object[0]);
        } else {
            Set<String> keySet = this.caffeineCache.asMap().keySet();
            Set<String> needRemoveKeys = new HashSet();
            Iterator var4 = keySet.iterator();

            while(var4.hasNext()) {
                String key = (String)var4.next();
                if (key.contains(patternKey.substring(0, patternKey.length() - 1))) {
                    needRemoveKeys.add(key);
                }
            }

            this.caffeineCache.invalidateAll(needRemoveKeys);
        }
    }
}
