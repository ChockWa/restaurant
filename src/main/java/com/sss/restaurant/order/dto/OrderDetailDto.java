package com.sss.restaurant.order.dto;

import com.sss.restaurant.order.model.Order;
import com.sss.restaurant.order.model.OrderDetail;

import java.util.List;

public class OrderDetailDto extends Order{

    // 商品列表
    private List<OrderDetail> orderDetailList;

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
