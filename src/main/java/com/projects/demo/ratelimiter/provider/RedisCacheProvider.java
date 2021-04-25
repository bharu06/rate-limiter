package com.projects.demo.ratelimiter.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class RedisCacheProvider implements ICacheProvider {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean put(String key, Object value) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value.toString());
        return true;
    }

    @Override
    public Object getOrDefault(String key, Object defaultValue) {
        //HashOperations vs ValueOperations
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Object value = valueOperations.get(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public void clearCacheForService(String serviceName) {
        //TODO
    }
}
