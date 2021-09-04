package com.redis.redislock;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

/**
 *8.（必做）基于 Redis 封装分布式数据操作：
 *
 * 在 Java 中实现一个简单的分布式锁；
 * 在 Java 中实现一个分布式计数器，模拟减库存。
 */
public class RedisLock {

    public enum EnumInstance {

        /**
         * 懒汉枚举单例
         */
        INSTANCE;
        private RedisLock instance;

        EnumInstance() {
            instance = new RedisLock();
        }

        public RedisLock getInstance() {
            return instance;
        }
    }


    //单例
    public static RedisLock getInstance() {
        return EnumInstance.INSTANCE.getInstance();
    }

    //redis 默认加载的时候本地的 6379 端口，可自己控制访问的ip和端口
    private JedisPool jedisPool = new JedisPool();

    public  JedisPool getJedisPool(){
        return jedisPool;
    }

    /**
     * 进行加锁
     *
     * @param lockValue lock value
     * @param seconds   expire time
     * @return get lock
     */
    public boolean lock(String lockValue, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            SetParams params = SetParams.setParams();
            // NX 代表只在键不存在时，才对键进行设置操作。
            params.nx();
            //PX 5000 设置键的过期时间为5000毫秒。
            params.px(seconds);
            //lockValue 是客户端生成的唯一的字符串。
            String result = jedis.set(lockValue, lockValue, params);
            if (result==null)
                return false;
            return result.equals("OK");
        }
    }

    /**
     * 释放锁
     * @param lock_key
     * @return
     */
    public boolean unLock(String lock_key){
//        用LUA脚本。先判断当前锁的字符串是否与传入的值相等，是的话就删除Key，解锁成功。
        String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                        "   return redis.call('del',KEYS[1]) " +
                        "else" +
                        "   return 0 " +
                        "end";
        try(Jedis jedis = jedisPool.getResource()) {
            return jedis.eval(luaScript, Collections.singletonList(lock_key), Collections.singletonList(lock_key)).equals(1L);
        }
    }
}
