package com.projects.demo.ratelimiter.ratelimiters;

import com.projects.demo.ratelimiter.dto.RequestInfo;
import com.projects.demo.ratelimiter.models.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterFactory {

    @Autowired
    MinuteRateLimiter minuteRateLimiter;

    @Autowired
    HourRateLimiter hourRateLimiter;

    public IRateLimiter getRateLimiter(TimeUnit timeUnit) {
        if (timeUnit.equals(TimeUnit.MINUTE)) {
            return minuteRateLimiter;
        }
        return hourRateLimiter;
    }

}
