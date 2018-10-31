package com.sss.restaurant.controller;

import com.sss.restaurant.table.dao.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("get")
    public String get(){
        return redisTemplate.opsForValue().get("name").toString();
    }

    @RequestMapping("set")
    public void set(){
        redisTemplate.opsForValue().set("name","hehehehehe");
    }

}
