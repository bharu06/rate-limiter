package com.projects.demo.ratelimiter.services;

import com.projects.demo.ratelimiter.models.ConsumerType;
import com.projects.demo.ratelimiter.models.RateLimit;
import com.projects.demo.ratelimiter.models.Service;
import com.projects.demo.ratelimiter.models.TimeUnit;
import com.projects.demo.ratelimiter.repositories.LimitRepository;
import com.projects.demo.ratelimiter.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.projects.demo.ratelimiter.constants.Constants.DEFAULT_API;

@Component
public class ServiceService {

    @Autowired
    ServiceRepository serviceRepository;


    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    public Service createService(String serviceName) {
        Service service = serviceRepository.findByName(serviceName);
        if (service == null) {
            service = serviceRepository.save(new Service(serviceName));
        }
        return service;
    }

    public long count() {
        return serviceRepository.count();
    }

}
