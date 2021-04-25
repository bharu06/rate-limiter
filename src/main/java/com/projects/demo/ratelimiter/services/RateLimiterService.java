package com.projects.demo.ratelimiter.services;

import com.projects.demo.ratelimiter.exception.ValidationException;
import com.projects.demo.ratelimiter.models.ConsumerType;
import com.projects.demo.ratelimiter.models.RateLimit;
import com.projects.demo.ratelimiter.models.Service;
import com.projects.demo.ratelimiter.models.TimeUnit;
import com.projects.demo.ratelimiter.repositories.LimitRepository;
import com.projects.demo.ratelimiter.repositories.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.projects.demo.ratelimiter.constants.Constants.DEFAULT_API;
import static com.projects.demo.ratelimiter.constants.Constants.DEFAULT_MINUTE_LIMIT;

@Component
public class RateLimiterService {

    Logger logger = LoggerFactory.getLogger(RateLimiterService.class);

    @Autowired
    LimitRepository limitRepository;

    @Autowired
    ServiceRepository serviceRepository;


    public List<RateLimit> findAll() {
        return limitRepository.findAll();
    }

    public List<RateLimit> findByService(String serviceName) throws ValidationException {
        Service service = serviceRepository.findByName(serviceName);
        if (service == null) {
            throw new ValidationException("Service " + serviceName + " not found");
        }
        return limitRepository.findByService(service.getId());
    }

    /**
     * Create RateLimit
     * @param serviceName serviceName
     * @param limit limit
     * @return RateLimit
     */
    public RateLimit createLimit(String serviceName, String apiName, Integer limit, TimeUnit timeUnit) {
        Service service = serviceRepository.findByName(serviceName);
        if (service == null) {
            service = serviceRepository.save(new Service(serviceName));
            //Create default rate limit as well. Default rate limit is 20 per minute
            RateLimit currentRateLimit = new RateLimit(service, ConsumerType.SERVICE, DEFAULT_MINUTE_LIMIT, DEFAULT_API, TimeUnit.MINUTE);
            limitRepository.save(currentRateLimit);
        }
        if (apiName == null) {
            apiName = DEFAULT_API;
        }
        if (timeUnit == null) {
            timeUnit = TimeUnit.MINUTE;
        }
        RateLimit currentRateLimit = new RateLimit(service, ConsumerType.SERVICE, limit, apiName, timeUnit);
        return limitRepository.save(currentRateLimit);
    }

    /**
     * Find RateLimit by service+apiName.
     * @param serviceName serviceName
     * @param apiName apiName
     * @return RateLimit
     * @throws ValidationException if service dosent exist
     */
    public RateLimit findLimit(String serviceName, String apiName) throws ValidationException {
        if (apiName == null) {
            return findLimit(serviceName);
        }
        Service service = serviceRepository.findByName(serviceName);
        if (service == null) {
            throw new ValidationException("Service " + serviceName + " not found");
        }
        RateLimit rateLimit = limitRepository.findByServiceAndApiName(service.getId(), apiName);
        if (rateLimit == null) {
            throw new ValidationException("RateLimit not configured for " + serviceName + "," + apiName);
        }
        return rateLimit;
    }

    /**
     * Find RateLimit by service+apiName.
     * @param serviceName serviceName
     * @return RateLimit
     * @throws ValidationException if service dosent exist
     */
    public RateLimit findLimit(String serviceName) throws ValidationException {
        Service service = serviceRepository.findByName(serviceName);
        if (service == null) {
            throw new ValidationException("Service " + serviceName + " not found");
        }
        RateLimit rateLimit = limitRepository.findByServiceAndApiName(service.getId(), DEFAULT_API);
        return rateLimit;
    }

}
