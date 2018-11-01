package com.sss.restaurant.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.sss.restaurant.common.info.UserInfo;
import com.sss.restaurant.common.response.Result;
import com.sss.restaurant.order.dto.PlaceOrderDto;
import com.sss.restaurant.order.dto.PrepareOrderDto;
import com.sss.restaurant.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 下单
     * @param list
     * @return
     */
    @RequestMapping(value = "placeOrder")
    public Result placeOrder(@RequestParam("goodsList")String list){
        List<PlaceOrderDto> goodsList = JSON.parseArray(list,PlaceOrderDto.class);
        PrepareOrderDto prepareOrderDto = orderService.placeOrder(UserInfo.getInfo().getTableUid(), UserInfo.getInfo().getUid()
                , goodsList);
        return Result.SUCCESS.setData(prepareOrderDto);
    }

    /**
     * 根据获取订单列表
     * @return
     */
    @RequestMapping("getOrderListByUid")
    public Result getOrderListByUid(){
        return Result.SUCCESS.setData("orderList",orderService.getOrderList(ImmutableMap.of("uid",UserInfo.getInfo().getUid())));
    }

    /**
     * 根据订单号查询订单详情
     * @param orderNo
     * @return
     */
    @RequestMapping("getOrderDetail")
    public Result getOrderDetail(@RequestParam("orderNo")String orderNo){
        return Result.SUCCESS.setData(orderService.getOrderDetail(orderNo));
    }
}
