package com.sss.restaurant.exception;

import com.sss.restaurant.common.exception.BizException;

/**
 * 商品异常类（1001开头）
 */
public class GoodsException extends BizException {

    public static final GoodsException GOODS_NOT_EXIST = new GoodsException(10010001,"商品不存在");

    public static final GoodsException GOODS_STOCK_NOT_ENOUGH = new GoodsException(10010002,"{0}商品库存不足");

    protected GoodsException(int code, String msg) {
        super(code, msg);
    }
}
