package com.projects.demo.ratelimiter.ratelimiters.stratergy;

import com.projects.demo.ratelimiter.dto.RequestInfo;
import com.projects.demo.ratelimiter.models.TimeUnit;
import com.projects.demo.ratelimiter.provider.ICacheProvider;

import java.util.Locale;

import static com.projects.demo.ratelimiter.ratelimiters.stratergy.STRATEGY.SLIDING;

public class StrategyFactory {



    public static IRateLimiterStratergy getRatelimiterStrategy(String strategy, ICacheProvider cacheProvider,
                                                               RequestInfo requestInfo, TimeUnit timeUnit) {
        if (strategy.equals(SLIDING.name().toLowerCase())) {
            return new SlidingWindowStrategy(cacheProvider, requestInfo, timeUnit);
        } else {
            return new FixedWindowStrategy(cacheProvider, requestInfo, timeUnit);
        }
    }
}
