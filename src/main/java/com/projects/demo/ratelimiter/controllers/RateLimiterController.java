package com.projects.demo.ratelimiter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.demo.ratelimiter.RequestLogger;
import com.projects.demo.ratelimiter.dto.RequestInfo;
import com.projects.demo.ratelimiter.models.RateLimit;
import com.projects.demo.ratelimiter.ratelimiters.LimitInfo;
import com.projects.demo.ratelimiter.ratelimiters.LimitStatus;
import com.projects.demo.ratelimiter.services.RateLimiterService;
import com.projects.demo.ratelimiter.services.ThrottlingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.transaction.Transactional;

import static com.projects.demo.ratelimiter.constants.Constants.DEFAULT_API;

@RestController
@Transactional
@RequestMapping("/api/")
public class RateLimiterController {


    Logger logger = LoggerFactory.getLogger(RateLimiterController.class);

    @Autowired
    ThrottlingService throttlingService;

    @Autowired
    RateLimiterService rateLimiterService;


    ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "Hit an api for a service")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success or Failure")
            }
    )
    @GetMapping(value = "/hit", produces = "application/json")
    public ResponseEntity<String> hit(@RequestHeader("service-name") String serviceName,
                                      @RequestHeader(value = "api-name", required = false) String apiName) throws Exception {

        RateLimit rateLimit = rateLimiterService.findLimit(serviceName, apiName);
        RequestInfo requestInfo = new RequestInfo(serviceName, apiName);
        requestInfo.setLimit(rateLimit.getValue());
        requestInfo.setApi(apiName);
        if (apiName == null) {
            requestInfo.setApi(DEFAULT_API);
        }
        LimitInfo limitInfo = throttlingService.isAllowed(requestInfo, rateLimit);
        if (limitInfo.getStatus().equals(LimitStatus.THROTTLED)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(objectMapper.writeValueAsString(limitInfo));
        }
        return ResponseEntity.ok(objectMapper.writeValueAsString(limitInfo));
    }



}
