package com.sss.restaurant.pay.service;

import com.sss.restaurant.exception.CommonException;
import com.sss.restaurant.exception.OrderException;
import com.sss.restaurant.order.contants.OrderStatusEnum;
import com.sss.restaurant.order.dao.OrderMapper;
import com.sss.restaurant.order.model.Order;
import com.sss.restaurant.pay.contants.PayStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PayService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 订单支付
     * @param orderNo
     */
    public Order payOrder(String orderNo){
        if(StringUtils.isBlank(orderNo))
            throw CommonException.PARAMS_NOT_NULL.format("订单号");

        // 目前默认支付成功
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null)
            throw OrderException.ORDER_NOT_EXIST;

        order.setPayStatus(PayStatusEnum.PAYED.getCode());
        order.setOrderStatus(OrderStatusEnum.UN_SEND.getCode());
        order.setPayTime(new Date());
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);

        return order;
    }

    /**
     * 支付成功进行商品解锁，更新库存
     * @param orderNo
     */
    private void paySuccUnlockUpdateStock(String orderNo){

    }
}
