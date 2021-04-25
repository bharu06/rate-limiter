package com.projects.demo.ratelimiter.ratelimiters;

import com.projects.demo.ratelimiter.models.TimeUnit;

public class LimitInfo {

    int limit;
    double currentRate;
    TimeUnit timeUnit;
    LimitStatus status;

    public LimitInfo(int limit, double currentRate, LimitStatus status, TimeUnit timeUnit) {
        this.limit = limit;
        this.status = status;
        this.currentRate = currentRate;
        this.timeUnit = timeUnit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public double getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(double currentRate) {
        this.currentRate = currentRate;
    }

    public LimitStatus getStatus() {
        return status;
    }

    public void setStatus(LimitStatus status) {
        this.status = status;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public String toString() {
        return "LimitInfo{" +
                "limit=" + limit +
                ", currentRate=" + currentRate +
                ", timeUnit=" + timeUnit +
                ", status=" + status +
                '}';
    }
}
