package com.projects.demo.ratelimiter.ratelimiters.stratergy;

import com.projects.demo.ratelimiter.dto.RequestInfo;
import com.projects.demo.ratelimiter.models.TimeUnit;
import com.projects.demo.ratelimiter.provider.ICacheProvider;
import com.projects.demo.ratelimiter.ratelimiters.LimitInfo;
import com.projects.demo.ratelimiter.ratelimiters.LimitStatus;

import java.util.HashMap;
import java.util.Map;

public class FixedWindowStrategy implements IRateLimiterStratergy {

    ICacheProvider cacheProvider;
    RequestInfo requestInfo;
    TimeUnit timeUnit;

    public FixedWindowStrategy(ICacheProvider cacheProvider, RequestInfo requestInfo, TimeUnit timeUnit) {
        this.cacheProvider = cacheProvider;
        this.requestInfo = requestInfo;
        this.timeUnit = timeUnit;
    }

    public LimitInfo isAllowed() {
        String key = calculateKey();
        int currentCount = Integer.parseInt(cacheProvider.getOrDefault(key, 0).toString());
        System.out.println("Key " + key + " value " + currentCount);
        if (currentCount < requestInfo.getLimit()) {
            cacheProvider.put(key, currentCount + 1);
            return new LimitInfo(requestInfo.getLimit(), currentCount, LimitStatus.ALLOWED, this.timeUnit);
        } else {
            return new LimitInfo(requestInfo.getLimit(), currentCount, LimitStatus.THROTTLED, this.timeUnit);
        }

    }


    String calculateKey() {
        if (this.timeUnit.equals(TimeUnit.MINUTE)) {
            long requestInfoTime = requestInfo.getTime();
            long timeInSeconds = requestInfoTime / 1000;
            long timeInMinutes = timeInSeconds / 60;
            return getKeyPrefix() + timeInMinutes;
        } else {
            long requestInfoTime = requestInfo.getTime();
            long timeInSeconds = requestInfoTime / 1000;
            long timeInMinutes = timeInSeconds / 60;
            long timeInHours = timeInMinutes / 60;
            return getKeyPrefix() + (timeInHours);
        }
    }

    String getKeyPrefix() {
        return requestInfo.getServiceName() + "_" + requestInfo.getApi() + "_";
    }


}
