package com.sss.restaurant.controller;

import com.sss.restaurant.common.response.Result;
import com.sss.restaurant.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 获取分类商品列表
     * @return
     */
    @RequestMapping("getCategoryGoodsList")
    public Result getCategoryGoodsList(){
        return Result.SUCCESS.setData("list",goodsService.getCategoryGoodsList(null));
    }
}
