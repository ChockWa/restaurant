package com.sss.restaurant.order.contants;

/**
 * 订单状态枚举
 */
public enum OrderStatusEnum {

    UN_PAY(1,"待付款"),
    UN_SEND(2,"待发货"),
    UN_RECEIVE(3,"待收货"),
    FINISH(4,"已完成"),
    CANCEL(5,"已取消");

    private int code;

    private String desc;

    private OrderStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
