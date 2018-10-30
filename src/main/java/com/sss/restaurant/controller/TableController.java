package com.sss.restaurant.controller;

import com.sss.restaurant.common.response.Result;
import com.sss.restaurant.table.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("table")
public class TableController {

    @Autowired
    private TableService tableService;

    /**
     * 开桌
     * @return
     */
    @RequestMapping("openTable")
    public Result openTable(){
        return Result.SUCCESS.setData("accessToken",tableService.openTable(null));
    }
}
