package com.projects.demo.ratelimiter;

import com.projects.demo.ratelimiter.provider.CacheProvider;
import com.projects.demo.ratelimiter.provider.ICacheProvider;
import com.projects.demo.ratelimiter.provider.InMemoryCacheProvider;
import com.projects.demo.ratelimiter.provider.RedisCacheProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.Locale;

@Configuration
public class ApplicationConfiguration {


    @Value("${cache-provider}")
    private String cacheProvider;

    @Bean
    CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new RequestLogger();
        filter.setIncludeQueryString(true);
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(true);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory();
        jedisConFactory.setHostName("localhost");
        jedisConFactory.setPort(6379);
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    @Primary
    ICacheProvider getCacheProvider() {
        if (cacheProvider.equals(CacheProvider.IN_MEMORY.name().toLowerCase(Locale.ROOT))) {
            return new InMemoryCacheProvider();
        } else {
            return new RedisCacheProvider();
        }
    }

}
