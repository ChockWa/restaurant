package com.sss.restaurant.order.service;

import com.sss.restaurant.order.dao.OrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 根据条件统计商品销售数量
     * @param query
     * @return
     */
    public int getGoodsSaleCountQuery(Map<String,Object> query){
        return orderDetailMapper.getGoodsSaleCountQuery(query);
    }
}
