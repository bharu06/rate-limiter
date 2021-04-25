package com.projects.demo.ratelimiter.provider;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class InMemoryCacheProvider  implements ICacheProvider {

    Map<String, Object> dataMap = new HashMap<>();

    @Override
    public boolean put(String key, Object value) {
        dataMap.put(key, value);
        return true;
    }

    @Override
    public Object getOrDefault(String key, Object defaultValue) {
        return dataMap.getOrDefault(key, defaultValue);
    }

    @Override
    public void clearCacheForService(String serviceName) {
        Set<String> keysToRemove = new HashSet<>();
        for (String key : dataMap.keySet()) {
            if (key.startsWith(serviceName)) {
                keysToRemove.add(key);
            }
        }
        for (String key : keysToRemove) {
            dataMap.remove(key);
        }
    }
}
