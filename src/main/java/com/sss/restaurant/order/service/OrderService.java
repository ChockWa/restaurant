package com.sss.restaurant.order.service;

import com.sss.restaurant.exception.GoodsException;
import com.sss.restaurant.exception.OrderException;
import com.sss.restaurant.goods.model.Goods;
import com.sss.restaurant.goods.service.GoodsService;
import com.sss.restaurant.order.dto.PlaceOrderDto;
import com.sss.restaurant.order.dto.PrepareOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private GoodsService goodsService;

    /**
     * 预下单，统计订单信息供展示
     * @param goodsList
     * @return
     */
    public PrepareOrderDto placeOrderPrepare(List<PlaceOrderDto> goodsList){
        if(goodsList == null || goodsList.size() < 1)
            throw OrderException.GOODS_NULL_ERROR;

        List<PlaceOrderDto> goodss = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal actAmount = BigDecimal.ZERO;
        for(PlaceOrderDto placeOrderDto : goodsList){
            Goods goods = goodsService.getGoodsById(placeOrderDto.getGoodsId());
            if(goods == null) throw GoodsException.GOODS_NOT_EXIST;

            // 计算单类商品的价格信息
            PlaceOrderDto goodsDetail = new PlaceOrderDto();
            goodsDetail.setGoodsId(placeOrderDto.getGoodsId());
            goodsDetail.setNumber(placeOrderDto.getNumber());
            goodsDetail.setGoodsName(goods.getName());
            BigDecimal goodsTotalAmount = goods.getAmountOrigin().multiply(new BigDecimal(placeOrderDto.getNumber()));
            BigDecimal goodsActAmount = goods.getAmount().multiply(new BigDecimal(placeOrderDto.getNumber()));
            goodsDetail.setTotalAmount(goodsTotalAmount);
            goodsDetail.setActAmount(goodsActAmount);
            goodss.add(goodsDetail);

            // 计算原来商品总金额
            totalAmount = totalAmount.add(goodsTotalAmount);

            // 计算原来商品实际总金额
            actAmount = actAmount.add(goodsActAmount);
        }

        PrepareOrderDto prepareOrderDto = new PrepareOrderDto();
        prepareOrderDto.setTotalAmount(totalAmount);
        prepareOrderDto.setActAmount(actAmount);
        prepareOrderDto.setDiscountAmount(totalAmount.subtract(actAmount));
        prepareOrderDto.setGoodsList(goodss);
        return prepareOrderDto;
    }
}
