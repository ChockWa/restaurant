package com.sss.restaurant.order.dto;

import java.math.BigDecimal;
import java.util.List;

public class PrepareOrderDto {

    // 原总价
    private BigDecimal totalAmount;

    // 实际总计
    private BigDecimal actAmount;

    // 优惠总价
    private BigDecimal discountAmount;

    // 商品列表
    private List<PlaceOrderDto> goodsList;
}
