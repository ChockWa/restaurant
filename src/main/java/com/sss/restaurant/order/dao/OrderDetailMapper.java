package com.sss.restaurant.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sss.restaurant.order.model.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail>{
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);

    /**
     * 根据条件统计商品销售数量
     * @param query
     * @return
     */
    int getGoodsSaleCountQuery(Map<String,Object> query);

    /**
     * 批量插入
     * @param goodsDetailList
     * @return
     */
    int insertBatch(@Param("goodsDetailList")List<OrderDetail> goodsDetailList);
}