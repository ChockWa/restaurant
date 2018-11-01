package com.sss.restaurant.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
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

        int code = UUID.randomUUID().toString().replace("-","").hashCode();
        code = code < 0 ? code * (-1) : code;
        return new StringBuilder(machineId).append(code).toString();
    }

    /**
     * 生成唯一订单号
     * @return
     */
    public static String genOrderNo(){
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String seconds = new SimpleDateFormat("HHmmss").format(new Date());
        return new StringBuilder(date+"00001000"+getTwo()+"00"+seconds+getTwo()).toString();
    }

    /**
     * 随机生成两位数
     * @return
     */
    public static String getTwo(){
        Random rad=new Random();
        String result  = rad.nextInt(100) +"";
        if(result.length()==1){
            result = "0" + result;
        }
        return result;
    }

    public static String genUuidNoLine(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
