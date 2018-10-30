package com.sss.restaurant.controller;

import com.sss.restaurant.common.info.TableUseInfo;
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
        PrepareOrderDto prepareOrderDto = orderService.placeOrder(TableUseInfo.getInfo().getAccessToken(),
                TableUseInfo.getInfo().getTableUid(), placeOrderBody.getGoodsList());
        return Result.SUCCESS.setData(prepareOrderDto);
    }
}
