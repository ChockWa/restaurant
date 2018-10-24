package com.sss.restaurant.common.response;


import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Result {

    private Integer code;

    private String msg;

    private Map<String,Object> data;

    public static Result SUCCESS = new Result(0,null,null);

    public Result(Integer code, String msg, Map<String, Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Object getData(String key){
        if(this.data == null)
            return null;

        return this.data.get(key);
    }

    public Result setData(Object obj) {
        try {
            BeanUtils.populate(obj,this.data);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Result setData(String key, Object value){
        if(this.data == null)
            this.data = new HashMap<>();

        this.data.put(key, value);
        return this;
    }

}
