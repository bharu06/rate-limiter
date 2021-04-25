package com.projects.demo.ratelimiter.ratelimiters;

import com.projects.demo.ratelimiter.dto.RequestInfo;
import com.projects.demo.ratelimiter.models.RateLimit;
import com.projects.demo.ratelimiter.models.TimeUnit;
import com.projects.demo.ratelimiter.provider.ICacheProvider;
import com.projects.demo.ratelimiter.ratelimiters.stratergy.FixedWindowStrategy;
import com.projects.demo.ratelimiter.ratelimiters.stratergy.IRateLimiterStratergy;
import com.projects.demo.ratelimiter.ratelimiters.stratergy.StrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MinuteRateLimiter implements IRateLimiter {

    @Autowired
    ICacheProvider cacheProvider;

    @Value("${minute-rate-limiter-strategy}")
    private String strategy;

    public LimitInfo shouldLimit(RequestInfo requestInfo, RateLimit rateLimit) {
        IRateLimiterStratergy rateLimiterStratergy = StrategyFactory.getRatelimiterStrategy(strategy,
                cacheProvider, requestInfo, TimeUnit.MINUTE);
        return rateLimiterStratergy.isAllowed();
    }

}
