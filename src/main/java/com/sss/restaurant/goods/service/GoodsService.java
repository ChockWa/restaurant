package com.sss.restaurant.goods.service;

import com.google.common.collect.ImmutableMap;
import com.sss.restaurant.common.utils.DateUtils;
import com.sss.restaurant.goods.dao.GoodsMapper;
import com.sss.restaurant.goods.dto.CategoryGoodsDto;
import com.sss.restaurant.goods.dto.GoodsDto;
import com.sss.restaurant.order.contants.OrderStatusEnum;
import com.sss.restaurant.order.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 获取分类商品列表
     * @param queryParams
     * @return
     */
    public List<CategoryGoodsDto> getCategoryGoodsList(Map<String,Object> queryParams){
        List<CategoryGoodsDto> list = goodsMapper.getCategoryGoodsList(queryParams);
        if(list == null || list.size() < 1) return null;

        // 获取每个商品每月销售量
        Date now = new Date();
        Date beginCreateTime = DateUtils.getFirstDayOfMonth(now);
        for(CategoryGoodsDto categoryGoodsDto : list){
            for(GoodsDto goodsDto : categoryGoodsDto.getGoodsList()){
                goodsDto.setSaleCountMonth(orderDetailService.getGoodsSaleCountQuery(ImmutableMap.of("goodsId",goodsDto.getGoodsId(),
                        "orderStatus", OrderStatusEnum.FINISH.getCode(),"beginCreateTime",beginCreateTime,"endCreateTime",now)));
            }
        }
        return list;
    }
}
