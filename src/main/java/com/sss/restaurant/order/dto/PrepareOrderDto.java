package com.sss.restaurant.order.dto;

import com.sss.restaurant.order.model.OrderDetail;

import java.math.BigDecimal;
import java.util.List;

public class PrepareOrderDto {

    // 原总价
    private BigDecimal totalAmount;

    // 实际总计
    private BigDecimal actAmount;

    // 优惠总价
    private BigDecimal discountAmount;

    // 订单号
    private String orderNo;

    // 商品列表
    private List<OrderDetail> goodsList;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

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

    public List<OrderDetail> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderDetail> goodsList) {
        this.goodsList = goodsList;
    }
}
