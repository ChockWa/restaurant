package com.sss.restaurant.controller;

import com.sss.restaurant.user.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("get")
    public List<Map<String,Object>> get(){
//        return null;
        return userMapper.getList();
    }
}
