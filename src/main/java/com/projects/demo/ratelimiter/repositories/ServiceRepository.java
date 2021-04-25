package com.projects.demo.ratelimiter.repositories;

import com.projects.demo.ratelimiter.models.RateLimit;
import com.projects.demo.ratelimiter.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {


    //TODO Optional?
    @Query("FROM Service s where s.name= :name")
    Service findByName(@Param("name") String name);

}