package com.projects.demo.ratelimiter.repositories;


import com.projects.demo.ratelimiter.models.RateLimit;
import com.projects.demo.ratelimiter.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LimitRepository extends JpaRepository<RateLimit, Long> {

    @Query("FROM RateLimit t where t.api= :api")
    List<RateLimit> findByApiName(@Param("api") String api);


    @Query("FROM RateLimit t where t.service.id= :serviceID")
    List<RateLimit> findByService(@Param("serviceID") long serviceID);

    @Query("FROM RateLimit t where t.service.id= :serviceID AND t.api = :api")
    RateLimit findByServiceAndApiName(@Param("serviceID") long serviceID,
                                      @Param("api") String api);
}