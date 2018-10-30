package com.sss.restaurant.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sss.restaurant.order.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order>{
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 根据订单号查询订单信息
     * @param orderNo
     * @return
     */
    Order selectByOrderNo(String orderNo);
}