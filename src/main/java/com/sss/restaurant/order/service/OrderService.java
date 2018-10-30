package com.sss.restaurant.order.service;

import com.sss.restaurant.common.lock.RedisKey;
import com.sss.restaurant.common.redis.RedisUtil;
import com.sss.restaurant.common.utils.GenNumberUtil;
import com.sss.restaurant.exception.CommonException;
import com.sss.restaurant.exception.GoodsException;
import com.sss.restaurant.exception.TableException;
import com.sss.restaurant.goods.model.Goods;
import com.sss.restaurant.goods.service.GoodsService;
import com.sss.restaurant.order.contants.OrderStatusEnum;
import com.sss.restaurant.order.dao.OrderDetailMapper;
import com.sss.restaurant.order.dao.OrderMapper;
import com.sss.restaurant.order.dto.PlaceOrderDto;
import com.sss.restaurant.order.dto.PrepareOrderDto;
import com.sss.restaurant.order.model.Order;
import com.sss.restaurant.order.model.OrderDetail;
import com.sss.restaurant.pay.contants.PayStatusEnum;
import com.sss.restaurant.table.contants.TableUserStatusEnum;
import com.sss.restaurant.table.dao.TableUseMapper;
import com.sss.restaurant.table.model.TableUse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private TableUseMapper tableUseMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 下单
     * @param uid
     * @param goodsList
     */
    @Transactional
    public PrepareOrderDto placeOrder(String accessToken, String uid, List<PlaceOrderDto> goodsList){
        if(goodsList == null || goodsList.size() < 1 || StringUtils.isBlank(uid) || StringUtils.isBlank(accessToken))
            throw CommonException.PARAMS_NOT_NULL.format("桌子id，accessToken，商品列表");

        // 查询是否存在桌子使用记录
        TableUse tableUse = checkTableUse(uid, accessToken);

        Lock goodsLock = new ReentrantLock();
        String orderNo = GenNumberUtil.genOrderNo(null);
        try{
            goodsLock.lock();
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
            orderMapper.insertSelective(order);

            // 更新桌子使用记录
            tableUse.setOrderNo(orderNo);
            tableUse.setSubmitTime(new Date());
            tableUse.setStatus(TableUserStatusEnum.ORDERED.getCode());
            tableUseMapper.updateByPrimaryKeySelective(tableUse);

            // 组装返回前端的数据
            PrepareOrderDto prepareOrderDto = new PrepareOrderDto();
            prepareOrderDto.setTotalAmount(totalAmount);
            prepareOrderDto.setActAmount(actAmount);
            prepareOrderDto.setDiscountAmount(totalAmount.subtract(actAmount));
            prepareOrderDto.setGoodsList(orderDetails);
            prepareOrderDto.setOrderNo(orderNo);
            return prepareOrderDto;
        }catch (Exception e) {
            // 若出现异常，解除库存锁定
            unLockOrderGoodsNum(orderNo,goodsList.stream().map(PlaceOrderDto::getGoodsId).collect(Collectors.toList()));
        }finally {
            goodsLock.unlock();
        }
        return null;
    }

    /**
     * 删除该订单下锁定的所有商品数量
     * @param orderNo
     * @param goodsIds
     */
    private void unLockOrderGoodsNum(String orderNo, List<Long> goodsIds){
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
     * 查询桌子是否绑定了token
     * @param uid
     * @param accessToken
     * @return
     */
    private TableUse checkTableUse(String uid, String accessToken){
        TableUse tableUse = tableUseMapper.selectByUidAndToken(accessToken,uid);
        if(tableUse == null)
            throw TableException.TABLE_USE_RECORD_NOT_EXIST;

        return tableUse;
    }
}
