package com.example.demo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@RestController
public class TestUnlock {

    @Autowired
    private RedisLockRegistry redisLockRegistry; // 自动注入我们创建的redisLockRegistry对象

    @GetMapping("/testUnlock/")
    public String testUnlock() throws InterruptedException {
        // 创建锁对象
        Lock lock = redisLockRegistry.obtain("lock_key");

        lock.tryLock(60, TimeUnit.SECONDS);
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return "ok";
    }
}
