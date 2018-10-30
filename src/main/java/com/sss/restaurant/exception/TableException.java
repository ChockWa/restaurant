package com.sss.restaurant.exception;

import com.sss.restaurant.common.exception.BizException;

/**
 * 桌子异常类（1002开头）
 */
public class TableException extends BizException {

    public static final TableException TABLE_NOT_EXIST = new TableException(10020001,"桌子不存在");

    public static final TableException TABLE_USE_RECORD_NOT_EXIST = new TableException(10020002,"桌子使用记录不存在");

    protected TableException(int code, String msg) {
        super(code, msg);
    }
}
