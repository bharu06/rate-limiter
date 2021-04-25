package com.projects.demo.ratelimiter.dto;

import com.projects.demo.ratelimiter.models.ConsumerType;
import com.projects.demo.ratelimiter.models.TimeUnit;

import java.util.Date;

public class RequestInfo {

    String serviceName;
    String api;
    long time;
    Integer limit;

    public RequestInfo() {
    }

    public RequestInfo(String serviceName, String api) {
        this.serviceName = serviceName;
        this.api = api;
        this.time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
