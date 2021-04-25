package com.projects.demo.ratelimiter.ratelimiters.stratergy;

import com.projects.demo.ratelimiter.dto.RequestInfo;
import com.projects.demo.ratelimiter.models.TimeUnit;
import com.projects.demo.ratelimiter.provider.ICacheProvider;
import com.projects.demo.ratelimiter.ratelimiters.LimitInfo;
import com.projects.demo.ratelimiter.ratelimiters.LimitStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlidingWindowStrategy implements IRateLimiterStratergy {

    ICacheProvider cacheProvider;
    RequestInfo requestInfo;
    TimeUnit timeUnit;
    Logger logger = LoggerFactory.getLogger(SlidingWindowStrategy.class);

    public SlidingWindowStrategy(ICacheProvider cacheProvider, RequestInfo requestInfo, TimeUnit timeUnit) {
        this.cacheProvider = cacheProvider;
        this.requestInfo = requestInfo;
        this.timeUnit = timeUnit;
    }

    /**
     * Return LimitInfo for the given request.
     * Algorithm :
     * We check whether we can allow the current request or not based on the previous window weight
     * and current weight.
     *  Rate = <current_rate> + (prev_rate * (prev_weight))
     *
     * For minute => prev_weight = Math.max(0, 0.95 - (currentSecond / 60))
     * For hour => prev_weight = Math.max(0, 0.95 - (currentMinute / 60))
     * @return LimitInfo
     */
    public LimitInfo isAllowed() {
        List<String> keys = calculateCurrentAndPreviousKeys();
        String currentKey = keys.get(1);
        String prevKey = keys.get(0);
        int prevCount = Integer.parseInt(cacheProvider.getOrDefault(prevKey, 0).toString());
        int currentCount = Integer.parseInt(cacheProvider.getOrDefault(currentKey, 0).toString());
        double prevWeight = getPrevWeight();
        double currentRateApprox = currentCount + (prevWeight * prevCount);
        logger.info("PKey " + prevKey + " value " + prevCount + " ,CurrentKey " + currentKey
                + " currentCount " + currentCount + " calculated rate approx " + currentRateApprox
                + " prev weight " + prevWeight);
        if (currentRateApprox < requestInfo.getLimit()) {
            cacheProvider.put(currentKey, currentCount + 1);
            return new LimitInfo(requestInfo.getLimit(), currentRateApprox, LimitStatus.ALLOWED, timeUnit);
        } else {
            return new LimitInfo(requestInfo.getLimit(), currentRateApprox, LimitStatus.THROTTLED, timeUnit);
        }

    }

    double getPrevWeight() {

        long requestInfoTime = requestInfo.getTime();
        long timeInSeconds = requestInfoTime / 1000;
        if (this.timeUnit.equals(TimeUnit.MINUTE)) {
            long currentSecond = timeInSeconds % 60;
            double prevWeight = Math.max(0, 0.95 - (currentSecond / 60.0));
            logger.info("Current seconds " + currentSecond + " prevWeight " + prevWeight);
            return prevWeight;
        } else {
            long timeInMinutes = timeInSeconds / 60;
            long currentMinute = timeInMinutes % 60;
            return Math.max(0, 0.95 - (currentMinute / 60.0));
        }
    }

    List<String> calculateCurrentAndPreviousKeys() {
        if (this.timeUnit.equals(TimeUnit.MINUTE)) {
            long requestInfoTime = requestInfo.getTime();
            long timeInSeconds = requestInfoTime / 1000;
            long timeInMinutes = timeInSeconds / 60;
            long prevTimeInMinutes = timeInMinutes - 1;
            return Arrays.asList(
                    getKeyPrefix() + (prevTimeInMinutes),
                    getKeyPrefix() + (timeInMinutes));
        } else {
            long requestInfoTime = requestInfo.getTime();
            long timeInSeconds = requestInfoTime / 1000;
            long timeInMinutes = timeInSeconds / 60;
            long timeInHours = timeInMinutes / 60;
            long prevTimeInHours = timeInHours - 1;
            return Arrays.asList(
                    getKeyPrefix() + (prevTimeInHours),
                    getKeyPrefix() + (timeInHours));
        }
    }

    String getKeyPrefix() {
        return requestInfo.getServiceName() + "_" + requestInfo.getApi() + "_";
    }
}
