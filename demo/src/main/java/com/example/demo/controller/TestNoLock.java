package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

@RestController
public class TestNoLock {
    private int count;

    @RequestMapping("/testNoLock/")
    public String test() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1000);
        count = 0;
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    int tempCount = count;
                    tempCount += 1;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                    count = tempCount;
                }finally {
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();
        System.out.println("final count is: " + count);

        return "ok";
    }
}
