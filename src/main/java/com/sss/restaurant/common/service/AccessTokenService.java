package com.sss.restaurant.common.service;

import com.sss.restaurant.common.utils.GenNumberUtil;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {

    /**
     * 生成token
     * @return
     */
    public String genAccessToken(){
        return GenNumberUtil.genUuidNoLine();
    }

}
