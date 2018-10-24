package com.sss.restaurant.goods.dto;

import java.math.BigDecimal;

/**
 * 商品信息dto
 */
public class GoodsDto {

    // 商品id
    private Long goodsId;

    // 商品名称
    private String goodsName;

    // 描述
    private String description;

    // 原始价格
    private BigDecimal amountOrigin;

    // 销售价
    private BigDecimal amount;

    // 折扣
    private BigDecimal discount;

    // 月销售量
    private int saleCountMonth;

    // 获得赞数
    private int praiseCount;

    // 库存
    private int stock;

    // 购买状态1-可购买2-已售光
    private Integer buyStatus;

    public Integer getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(Integer buyStatus) {
        this.buyStatus = buyStatus;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmountOrigin() {
        return amountOrigin;
    }

    public void setAmountOrigin(BigDecimal amountOrigin) {
        this.amountOrigin = amountOrigin;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public int getSaleCountMonth() {
        return saleCountMonth;
    }

    public void setSaleCountMonth(int saleCountMonth) {
        this.saleCountMonth = saleCountMonth;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }
}
