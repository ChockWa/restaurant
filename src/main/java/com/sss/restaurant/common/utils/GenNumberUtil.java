package com.sss.restaurant.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.util.UUID;

public class GenNumberUtil {

    /**
     * 生成唯一订单号
     * @param machineId  机器id
     * @return
     */
    public static String genOrderNo(String machineId){
        if(StringUtils.isBlank(machineId))
            machineId = "1";

        return new StringBuilder(machineId).append(UUID.randomUUID().toString().replace("_","").hashCode()).toString();
    }

    public static String genUuidNoLine(){
        return UUID.randomUUID().toString().replace("_","");
    }
}
