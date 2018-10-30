package com.sss.restaurant.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class BeanUtils {

    /**
     * bean转map(只能用于bean，不能用于list对象)
     * @param obj
     * @return
     */
    public static Map<String,Object> bean2Map(Object obj){
        if(obj == null) return null;

        if(obj instanceof Map){
            return (Map<String, Object>) obj;
        }else{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(obj,Map.class);
        }
    }
}
