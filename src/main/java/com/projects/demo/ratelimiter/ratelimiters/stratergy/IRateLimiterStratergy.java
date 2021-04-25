package com.projects.demo.ratelimiter.ratelimiters.stratergy;

import com.projects.demo.ratelimiter.ratelimiters.LimitInfo;

public interface IRateLimiterStratergy {
    LimitInfo isAllowed();
}
