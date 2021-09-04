package com.redis.redislock;


import redis.clients.jedis.Jedis;

/**
 * 8.（必做）基于 Redis 封装分布式数据操作：
 * 在 Java 中实现一个分布式计数器，模拟减库存。
 */
public class CountUtil implements Runnable{

    public CountUtil(String keyName,int count) {
        this.keyName = keyName;
        //初始化总库存数量到redis  ，也可查询数据库
        try (Jedis jedis = RedisLock.getInstance().getJedisPool().getResource()) {
            jedis.set(keyName, count + "");
        }
    }

    /**
     * 键
     */
    private String keyName = "";
    //锁时间
    private int seconds = 5;

    public void excuteTask() {
        System.out.println("当前线程线程：" + Thread.currentThread().getName());
        // 加锁
       boolean bo= RedisLock.getInstance().lock(keyName, seconds);
       if (!bo) {
           System.out.println("加锁失败");
           return;
       }
        try (Jedis jedis = RedisLock.getInstance().getJedisPool().getResource()) {
            Integer valueInt = Integer.parseInt(jedis.get(keyName));//库存量
            if (valueInt > 0) {
                Integer result = valueInt - 1;
                jedis.set(keyName, result + "");
                System.out.println("出库成功，剩余：" + result);
            } else {
                System.out.println("库存不足，剩余：" + valueInt);
            }
        }
        //释放锁
        RedisLock.getInstance().unLock(keyName);
    }

    @Override
    public void run() {
        excuteTask();
    }
}
