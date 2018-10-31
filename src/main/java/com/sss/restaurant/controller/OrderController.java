package com.sss.restaurant.controller;

import com.google.common.collect.ImmutableMap;
import com.sss.restaurant.common.info.UserInfo;
import com.sss.restaurant.common.response.Result;
import com.sss.restaurant.order.dto.PlaceOrderBody;
import com.sss.restaurant.order.dto.PrepareOrderDto;
import com.sss.restaurant.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 下单
     * @param placeOrderBody
     * @return
     */
    @RequestMapping("placeOrder")
    public Result placeOrder(@RequestBody PlaceOrderBody placeOrderBody){
        PrepareOrderDto prepareOrderDto = orderService.placeOrder(UserInfo.getInfo().getTableUid(), UserInfo.getInfo().getUid()
                , placeOrderBody.getGoodsList());
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
