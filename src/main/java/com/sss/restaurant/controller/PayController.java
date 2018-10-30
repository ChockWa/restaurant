package com.sss.restaurant.controller;

import com.sss.restaurant.common.response.Result;
import com.sss.restaurant.order.model.Order;
import com.sss.restaurant.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pay")
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 订单支付
     * @param orderNo
     * @return
     */
    @RequestMapping("payOrder")
    public Result payOrder(@RequestParam("orderNo")String orderNo){
        Order order = payService.payOrder(orderNo);
        return Result.SUCCESS.setData(order);
    }
}
