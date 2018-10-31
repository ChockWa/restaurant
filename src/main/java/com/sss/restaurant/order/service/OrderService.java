package com.sss.restaurant.order.service;

import com.alibaba.fastjson.JSON;
import com.sss.restaurant.common.lock.CommonLock;
import com.sss.restaurant.common.lock.RedisKey;
import com.sss.restaurant.common.redis.RedisUtil;
import com.sss.restaurant.common.utils.GenNumberUtil;
import com.sss.restaurant.exception.CommonException;
import com.sss.restaurant.exception.GoodsException;
import com.sss.restaurant.goods.model.Goods;
import com.sss.restaurant.goods.service.GoodsService;
import com.sss.restaurant.order.contants.OrderStatusEnum;
import com.sss.restaurant.order.dao.OrderDetailMapper;
import com.sss.restaurant.order.dao.OrderMapper;
import com.sss.restaurant.order.dto.OrderDetailDto;
import com.sss.restaurant.order.dto.PlaceOrderDto;
import com.sss.restaurant.order.dto.PrepareOrderDto;
import com.sss.restaurant.order.model.Order;
import com.sss.restaurant.order.model.OrderDetail;
import com.sss.restaurant.pay.contants.PayStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 下单
     * @param uid
     * @param goodsList
     */
    @Transactional
    public PrepareOrderDto placeOrder(String tableUid, String uid, List<PlaceOrderDto> goodsList){
        if(goodsList == null || goodsList.size() < 1 || StringUtils.isBlank(uid) || StringUtils.isBlank(tableUid))
            throw CommonException.PARAMS_NOT_NULL.format("桌子id，用户id，商品列表");

        String orderNo = GenNumberUtil.genOrderNo(null);
        try{
            CommonLock.STOCK_LOCK.lock();
            // 生成订单号
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal actAmount = BigDecimal.ZERO;
            List<OrderDetail> orderDetails = new ArrayList<>();
            for(PlaceOrderDto placeOrderDto : goodsList){
                Goods goods = goodsService.getGoodsById(placeOrderDto.getGoodsId());
                if(goods == null) throw GoodsException.GOODS_NOT_EXIST;

                // 判断库存
                int lockGoodsNum = getLockGoodsNum(goods.getId()); // 获取该商品已锁定的数量
                if(goods.getStock() - lockGoodsNum < placeOrderDto.getNumber()){
                    throw GoodsException.GOODS_STOCK_NOT_ENOUGH.format(goods.getName());
                }
                // 锁定库存(用订单号加商品id作为锁名称)
                redisUtil.getAndSet(RedisKey.GOODS_LOCK + goods.getId() + orderNo, placeOrderDto.getNumber());

                // 组装订单详情列表
                BigDecimal goodsTotalAmount = goods.getAmountOrigin().multiply(new BigDecimal(placeOrderDto.getNumber()));
                BigDecimal goodsActAmount = goods.getAmount().multiply(new BigDecimal(placeOrderDto.getNumber()));
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setAmountOrigin(goodsTotalAmount);
                orderDetail.setAmount(goodsActAmount);
                orderDetail.setDiscount(goodsActAmount.subtract(goodsActAmount));
                orderDetail.setGoodsId(goods.getId());
                orderDetail.setNum(placeOrderDto.getNumber());
                orderDetail.setCreateTime(new Date());
                orderDetail.setGoodsName(goods.getName());
                orderDetails.add(orderDetail);

                totalAmount = totalAmount.add(goodsTotalAmount);
                actAmount = actAmount.add(goodsActAmount);
            }
            orderDetailMapper.insertBatch(orderDetails);

            // 生成订单
            Order order = new Order();
            order.setOrderNo(orderNo);
            order.setGoodsNum(goodsList.stream().mapToInt(goods -> goods.getNumber()).sum());
            order.setTotalAmount(totalAmount);
            order.setActAmount(actAmount);
            order.setCreateTime(new Date());
            order.setDiscount(totalAmount.subtract(actAmount));
            order.setPlaceOrderTime(new Date());
            order.setOrderStatus(OrderStatusEnum.UN_PAY.getCode());
            order.setPayStatus(PayStatusEnum.UN_PAY.getCode());
            order.setUid(uid);
            order.setTableUid(tableUid);
            orderMapper.insertSelective(order);

            // 组装返回前端的数据
            PrepareOrderDto prepareOrderDto = new PrepareOrderDto();
            prepareOrderDto.setTotalAmount(totalAmount);
            prepareOrderDto.setActAmount(actAmount);
            prepareOrderDto.setDiscountAmount(totalAmount.subtract(actAmount));
            prepareOrderDto.setGoodsList(orderDetails);
            prepareOrderDto.setOrderNo(orderNo);
            return prepareOrderDto;
        }catch (Exception e) {
            logger.error("生成订单失败,用户id:{},桌子id{},商品{}",new Object[]{uid,tableUid, JSON.toJSON(goodsList)});
            // 若出现异常，解除库存锁定
            unLockOrderGoodsNum(orderNo,goodsList.stream().map(PlaceOrderDto::getGoodsId).collect(Collectors.toList()));
            throw e;
        }finally {
            CommonLock.STOCK_LOCK.unlock();
        }
    }

    /**
     * 删除该订单下锁定的所有商品数量
     * @param orderNo
     * @param goodsIds
     */
    public void unLockOrderGoodsNum(String orderNo, List<Long> goodsIds){
        List<String> keys = new ArrayList<>();
        for(Long goodsId : goodsIds){
            keys.add(RedisKey.GOODS_LOCK + String.valueOf(goodsId) + orderNo);
        }
        redisUtil.deleteByKeys(keys);
    }

    /**
     * 从缓存中获取被锁定的商品总数
     * @param goodsId
     * @return
     */
    private int getLockGoodsNum(Long goodsId){
        if(goodsId == null)
            return 0;

        int lockTotal = 0;
        Set<String> keys = redisUtil.keys(RedisKey.GOODS_LOCK + goodsId);
        for(String key : keys){
            lockTotal += (int)redisUtil.get(key);
        }
        return lockTotal;
    }

    /**
     * 根据订单号获取订单详情
     * @param order
     * @return
     */
    public OrderDetailDto getOrderDetail(String order){
        if(StringUtils.isBlank(order)) return null;

        return orderDetailMapper.getOrderDetail(order);
    }

    /**
     * 获取订单列表
     * @param params
     * @return
     */
    public List<Order> getOrderList(Map<String, Object> params){
        return orderMapper.getOrderList(params);
    }

}
