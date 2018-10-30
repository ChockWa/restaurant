package com.sss.restaurant.exception;

import com.sss.restaurant.common.exception.BizException;

/**
 * 通用异常类（9000开头）
 */
public class CommonException extends BizException {

    public static final CommonException PARAMS_NOT_NULL = new CommonException(90000001,"{0}参数不能为空");

    public static final CommonException TOKEN_EXPIRE = new CommonException(90000002,"会话超时");

    protected CommonException(int code, String msg) {
        super(code, msg);
    }
}
