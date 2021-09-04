package com.redis.redislock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedislockApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RedislockApplication.class, args);

        //多线程 库存出库
//        在 Java 中实现一个分布式计数器，模拟减库存。
        CountUtil countUtil=new CountUtil("kucun",10);
        Thread thread1 = new Thread(countUtil);
        Thread thread2 = new Thread(countUtil);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Thread thread3 = new Thread(countUtil);
        thread3.start();
        thread3.join();
    }
}
