package com.projects.demo.ratelimiter.unittests;

import com.projects.demo.ratelimiter.dto.RequestInfo;

public class DataProvider {

    public static RequestInfo getSampleRequest(String serviceName, String api) {
        RequestInfo requestInfo = new RequestInfo(serviceName, api);
        return  requestInfo;
    }
}
