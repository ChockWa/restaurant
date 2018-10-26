package com.sss.restaurant.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private static final long DEFAULT_EXPIRE_TIME = 24 * 60 * 60 * 1000; //默认一天

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean setIfNotExist(String key, Object value){
        return redisTemplate.opsForValue().setIfAbsent(key,value);
    }

    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value, DEFAULT_EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    public void set(String key, Object value, long milliSecond){
        if(milliSecond != 0)
            redisTemplate.opsForValue().set(key, value, milliSecond, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(key, value, DEFAULT_EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Object getAndSet(String key, Object value){
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public Set<String> keys(String keyPrefix){
        return redisTemplate.keys(keyPrefix);
    }

    public long inc(String key) {
        return incBy(key, 1);
    }

    public long incBy(String key, int step){
        return redisTemplate.opsForValue().increment(key,step);
    }
}
