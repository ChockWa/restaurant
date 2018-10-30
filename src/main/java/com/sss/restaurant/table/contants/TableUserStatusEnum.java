package com.sss.restaurant.table.contants;

public enum TableUserStatusEnum {

    ORDERING(1,"点餐中"),
    ORDERED(2,"点餐完毕"),
    ORDER_OVER(3,"上餐完毕");

    private int code;

    private String desc;

    TableUserStatusEnum(int code, String desc) {
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
