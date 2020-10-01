package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
public class RedisLock {

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, "registry_key");
    }
}
