package com.sss.restaurant.order.dto;

import java.math.BigDecimal;

public class PlaceOrderDto {

    // 商品id
    private Long goodsId;

    // 下单数量
    private int number;

    // 商品名称
    private String goodsName;

    // 该类商品的原始总价
    private BigDecimal totalAmount;

    // 该类商品的实际总价
    private BigDecimal actAmount;

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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
