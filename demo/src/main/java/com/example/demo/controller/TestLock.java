package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

@RestController
public class TestLock {

    private int count;

    @Autowired
    private RedisLockRegistry redisLockRegistry; // 自动注入我们创建的redisLockRegistry对象

    @GetMapping("/testLock/")
    public String testLock() throws InterruptedException {

        // 创建锁对象
        Lock lock = redisLockRegistry.obtain("lock_key");

        CountDownLatch countDownLatch = new CountDownLatch(1000);
        count = 0;
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                // 加锁
                lock.lock();
                try {
                    int tempCount = count;
                    tempCount += 1;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                    count = tempCount;
                } finally {
                    countDownLatch.countDown();
                    // finally中确保释放锁
                    lock.unlock();
                }
            }).start();
        }

        countDownLatch.await();
        System.out.println("final count is: " + count);

        return "ok";
    }

}
