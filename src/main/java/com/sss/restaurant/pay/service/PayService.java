package com.sss.restaurant.pay.service;

import com.google.common.collect.ImmutableMap;
import com.sss.restaurant.common.lock.CommonLock;
import com.sss.restaurant.exception.CommonException;
import com.sss.restaurant.exception.OrderException;
import com.sss.restaurant.goods.dao.GoodsMapper;
import com.sss.restaurant.order.contants.OrderStatusEnum;
import com.sss.restaurant.order.dao.OrderDetailMapper;
import com.sss.restaurant.order.dao.OrderMapper;
import com.sss.restaurant.order.model.Order;
import com.sss.restaurant.order.model.OrderDetail;
import com.sss.restaurant.order.service.OrderService;
import com.sss.restaurant.pay.contants.PayStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PayService {

    private static final Logger logger = LoggerFactory.getLogger(PayService.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderService orderService;

    /**
     * 订单支付
     * @param orderNo
     */
    @Transactional
    public Order payOrder(String orderNo){
        if(StringUtils.isBlank(orderNo))
            throw CommonException.PARAMS_NOT_NULL.format("订单号");

        // 目前默认支付成功
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null)
            throw OrderException.ORDER_NOT_EXIST;

        order.setPayStatus(PayStatusEnum.PAYED.getCode());
        order.setOrderStatus(OrderStatusEnum.UN_SEND.getCode());
        order.setPayTime(new Date());
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);

        // 解锁更新库存
        paySuccUnlockUpdateStock(order);

        return order;
    }

    /**
     * 支付成功进行商品解锁，更新库存
     * @param order
     */
    private void paySuccUnlockUpdateStock(Order order){
        // 查询该订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.getOrderDetailListByOrderNo(order.getOrderNo());
        try{
            CommonLock.STOCK_LOCK.lock();
            List<Long> goodsIds = new ArrayList<>();
            for(OrderDetail orderDetail : orderDetailList){
                // 减相应的库存
                goodsMapper.updateStock(ImmutableMap.of("id",orderDetail.getGoodsId(),"number",orderDetail.getNum()));
                goodsIds.add(orderDetail.getGoodsId());
            }
            // 解锁
            orderService.unLockOrderGoodsNum(order.getOrderNo(),goodsIds);
        }catch(Exception e){
            logger.error("订单支付成功,解锁更新库存失败,订单号:{}",order.getOrderNo());
            throw e;
        }finally {
            CommonLock.STOCK_LOCK.unlock();
        }
    }
}
