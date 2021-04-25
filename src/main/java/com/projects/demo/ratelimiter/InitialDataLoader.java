package com.projects.demo.ratelimiter;

import com.projects.demo.ratelimiter.models.Service;
import com.projects.demo.ratelimiter.models.TimeUnit;
import com.projects.demo.ratelimiter.services.RateLimiterService;
import com.projects.demo.ratelimiter.services.ThrottlingService;
import com.projects.demo.ratelimiter.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.projects.demo.ratelimiter.constants.Constants.DEFAULT_API;

@Component
public class InitialDataLoader implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    ServiceService serviceService;

    @Autowired
    RateLimiterService rateLimiterService;

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        String defaultService = "sampleservice";
        List<Service> services = new ArrayList<>();
        for (int i=0;i<10;i++) {
            services.add(serviceService.createService(defaultService + "_" + i));
        }
        //Add limits for default api

        for (Service service : services) {
            rateLimiterService.createLimit(service.getName(), DEFAULT_API, 10, TimeUnit.MINUTE);
        }

        System.out.println("Added " + rateLimiterService.findAll().size() + " limits ");
        System.out.println("Added " + serviceService.count() + " services ");

        return;
    }
}