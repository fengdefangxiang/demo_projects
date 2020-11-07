package com.example.demo;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.CountDownLatch;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        Param[] params = new Param[1000];
        for (int i = 0; i < 1000; i++) {
            params[i] = Param.builder().id(i).build();
        }

        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (Param param : params) {
            // System.identityHashCode可以打印对象的内存地址
            System.out.println("get param: " + System.identityHashCode(param));
            Worker worker = new Worker(param, countDownLatch);
            new Thread(worker).start();
        }

        countDownLatch.await();
    }
}

@Data
@Builder
class Param {
    private Integer id;
}

class Worker implements Runnable {

    private Param param;

    private CountDownLatch countDownLatch;

    public Worker(Param param, CountDownLatch countDownLatch) {
        this.param = param;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("deal param: " + param.toString());
        countDownLatch.countDown();
    }
}
