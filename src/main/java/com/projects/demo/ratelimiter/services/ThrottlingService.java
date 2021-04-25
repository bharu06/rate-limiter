package com.projects.demo.ratelimiter.services;

import com.projects.demo.ratelimiter.dto.RequestInfo;
import com.projects.demo.ratelimiter.models.RateLimit;
import com.projects.demo.ratelimiter.ratelimiters.LimitInfo;
import com.projects.demo.ratelimiter.ratelimiters.RateLimiterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThrottlingService {

    @Autowired
    RateLimiterFactory rateLimiterFactory;

    public LimitInfo isAllowed(RequestInfo requestInfo, RateLimit rateLimit) {
        return rateLimiterFactory.getRateLimiter(rateLimit.getTimeUnit()).shouldLimit(requestInfo, rateLimit);
    }
}
