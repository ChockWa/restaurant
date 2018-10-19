package com.sss.restaurant.controller;

import com.sss.restaurant.table.dao.TableMapper;
import com.sss.restaurant.table.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TableMapper tableMapper;

    @RequestMapping("get")
    public Table get(){
//        return null;
        return tableMapper.selectByPrimaryKey(1L);
    }
}
