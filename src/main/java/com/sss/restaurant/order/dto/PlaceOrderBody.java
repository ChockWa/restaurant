package com.sss.restaurant.order.dto;

import java.util.List;

/**
 * 下单请求体dto
 */
public class PlaceOrderBody {

    // 商品列表
    List<PlaceOrderDto> goodsList;

    public List<PlaceOrderDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<PlaceOrderDto> goodsList) {
        this.goodsList = goodsList;
    }
}
