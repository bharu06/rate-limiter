package com.projects.demo.ratelimiter.dto;

import com.projects.demo.ratelimiter.models.ConsumerType;
import com.projects.demo.ratelimiter.models.Service;
import com.projects.demo.ratelimiter.models.TimeUnit;

import javax.persistence.*;

public class RateLimitDTO {

    String serviceName;

    ConsumerType consumerType;

    TimeUnit timeUnit;

    Integer limit;

    String api;

    public RateLimitDTO() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ConsumerType getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(ConsumerType consumerType) {
        this.consumerType = consumerType;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
