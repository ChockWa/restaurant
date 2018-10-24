package com.sss.restaurant.exception;

import com.sss.restaurant.common.exception.BizException;

/**
 * 订单异常类（1000开头）
 */
public class OrderException extends BizException {

    public static final OrderException GOODS_NULL_ERROR = new OrderException(10000001,"商品不能为空");

    protected OrderException(int code, String msg) {
        super(code, msg);
    }
}
