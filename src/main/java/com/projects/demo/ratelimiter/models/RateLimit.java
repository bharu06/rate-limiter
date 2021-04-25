package com.projects.demo.ratelimiter.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ratelimits")
public class RateLimit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ConsumerType consumerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TimeUnit timeUnit;

    //Cant name column name as limit due to being a reserved keyword
    @Column(nullable = false)
    Integer value;

    @Column(nullable = false)
    String api;

    //Default constructor otherwise jpa will throw no default constructor error.
    public RateLimit() {

    }

    public RateLimit(Service service, ConsumerType consumerType, Integer value, String api, TimeUnit timeUnit) {
        this.id = id;
        this.service = service;
        this.consumerType = consumerType;
        this.value = value;
        this.timeUnit = timeUnit;
        this.api = api;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public ConsumerType getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(ConsumerType consumerType) {
        this.consumerType = consumerType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
