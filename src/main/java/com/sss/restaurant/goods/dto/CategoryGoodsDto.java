package com.sss.restaurant.goods.dto;

import java.util.List;

/**
 * 分类商品信息dto
 */
public class CategoryGoodsDto {

    private String categoryName;

    private List<GoodsDto> goodsList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<GoodsDto> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsDto> goodsList) {
        this.goodsList = goodsList;
    }
}
