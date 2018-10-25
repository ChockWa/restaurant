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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getActAmount() {
        return actAmount;
    }

    public void setActAmount(BigDecimal actAmount) {
        this.actAmount = actAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public List<PlaceOrderDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<PlaceOrderDto> goodsList) {
        this.goodsList = goodsList;
    }
}
