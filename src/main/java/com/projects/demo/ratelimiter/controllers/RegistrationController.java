package com.projects.demo.ratelimiter.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.demo.ratelimiter.dto.RateLimitDTO;
import com.projects.demo.ratelimiter.exception.ValidationException;
import com.projects.demo.ratelimiter.models.RateLimit;
import com.projects.demo.ratelimiter.services.RateLimiterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@RequestMapping("/api/")
public class RegistrationController {


    @Autowired
    RateLimiterService rateLimiterService;

    ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "List of all services and rate limits configured")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "All services and rate limits configured")
            }
    )
    @GetMapping(value = "/ratelimits/list", produces = "application/json")
    public ResponseEntity<String> list() throws Exception {
        List<RateLimit> limitList = rateLimiterService.findAll();
        if (limitList.size() == 0) {
            return ResponseEntity.ok("{ \"message\": \"No rate limits found\"}");
        }
        return ResponseEntity.ok(objectMapper.valueToTree(limitList).toString());
    }

    @ApiOperation(value = "Get ratelimits info for a service")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "ratelimits list")
            }
    )
    @GetMapping(value = "/ratelimits/listbyservice", produces = "application/json")
    public ResponseEntity<String> list(@RequestParam(name = "servicename") String servicename) throws Exception {
        List<RateLimit> limitList = rateLimiterService.findByService(servicename);
        if (limitList.size() == 0) {
            return ResponseEntity.ok("{ \"message\": \"No rate limits found\"}");
        }
        return ResponseEntity.ok(objectMapper.valueToTree(limitList).toString());
    }


    @ApiOperation(value = "Register a service + api")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "New Registration Info")
            }
    )
    @PostMapping(value = "/ratelimits/create", produces = "application/json")
    public ResponseEntity<String> create(@RequestBody RateLimitDTO rateLimitDTO) throws Exception {
        if (rateLimitDTO.getServiceName() == null) {
            throw new ValidationException("Body with field serviceName cant be null");
        }
        RateLimit rateLimit = rateLimiterService.createLimit(rateLimitDTO.getServiceName(), rateLimitDTO.getApi(),
                rateLimitDTO.getLimit(), rateLimitDTO.getTimeUnit());
        return ResponseEntity.ok(objectMapper.valueToTree(rateLimit).toString());
    }

}
