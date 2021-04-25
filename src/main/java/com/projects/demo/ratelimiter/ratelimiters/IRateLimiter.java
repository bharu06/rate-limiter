package com.projects.demo.ratelimiter.ratelimiters;

import com.projects.demo.ratelimiter.dto.RequestInfo;
import com.projects.demo.ratelimiter.models.RateLimit;

public interface IRateLimiter {

    LimitInfo shouldLimit(RequestInfo requestInfo, RateLimit rateLimit);

}
