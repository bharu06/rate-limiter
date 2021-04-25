package com.projects.demo.ratelimiter.unittests;

import com.projects.demo.ratelimiter.dto.RequestInfo;
import com.projects.demo.ratelimiter.models.TimeUnit;
import com.projects.demo.ratelimiter.provider.ICacheProvider;
import com.projects.demo.ratelimiter.provider.InMemoryCacheProvider;
import com.projects.demo.ratelimiter.ratelimiters.LimitInfo;
import com.projects.demo.ratelimiter.ratelimiters.LimitStatus;
import com.projects.demo.ratelimiter.ratelimiters.stratergy.FixedWindowStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class FixedWindowStrategyTest {

    public static final String DEFAULT_SERVICE_NAME = "test_service";
    public static final String API_NAME = "api_name";

    ICacheProvider cacheProvider;

    @Before
    public void setup() {
        cacheProvider = new InMemoryCacheProvider();
    }

    @Test
    public void failureTest() {
        int requests = 20;
        int failedCount = 0;
        for (int i=0;i<requests;i++) {
            RequestInfo requestInfo = DataProvider.getSampleRequest(DEFAULT_SERVICE_NAME, API_NAME);
            requestInfo.setLimit(10);
            FixedWindowStrategy fixedWindowStrategy = new FixedWindowStrategy(cacheProvider, requestInfo, TimeUnit.MINUTE);
            LimitInfo limitInfo = fixedWindowStrategy.isAllowed();
            System.out.println(limitInfo);
            if (limitInfo.getStatus().equals(LimitStatus.THROTTLED)) {
                failedCount++;
            }
        }

        Assert.assertEquals("Should receive 5 throttled requests at least", true,failedCount > 5);

    }
}
