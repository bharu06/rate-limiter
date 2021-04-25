package com.projects.demo.ratelimiter.provider;

public interface ICacheProvider {

    boolean put(String key, Object value);
    Object getOrDefault(String key, Object defaultValue);
    void clearCacheForService(String key);

}
