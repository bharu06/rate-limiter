package com.projects.demo.ratelimiter;


import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class RequestLogger extends CommonsRequestLoggingFilter {


    public RequestLogger() {
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return this.logger.isInfoEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        this.logger.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        //SKIP
    }
}
