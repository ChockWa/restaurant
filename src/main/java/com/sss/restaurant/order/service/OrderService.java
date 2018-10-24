package com.sss.restaurant.order.service;

import com.sss.restaurant.exception.OrderException;
import com.sss.restaurant.order.dto.PlaceOrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    public void placeOrderPrepare(List<PlaceOrderDto> goods){
        if(goods == null || goods.size() < 1)
            throw OrderException.GOODS_NULL_ERROR;
    }
}
