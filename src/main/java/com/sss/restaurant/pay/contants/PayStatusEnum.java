package com.sss.restaurant.pay.contants;

public enum PayStatusEnum {

    UN_PAY(1,"未付款"),
    PAYED(2,"已付款");

    private int code;

    private String desc;

    private PayStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
