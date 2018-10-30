package com.sss.restaurant.table.service;

import com.sss.restaurant.common.redis.RedisUtil;
import com.sss.restaurant.common.service.AccessTokenService;
import com.sss.restaurant.exception.CommonException;
import com.sss.restaurant.exception.TableException;
import com.sss.restaurant.table.contants.TableUserStatusEnum;
import com.sss.restaurant.table.dao.TableMapper;
import com.sss.restaurant.table.dao.TableUseMapper;
import com.sss.restaurant.table.model.Table;
import com.sss.restaurant.table.model.TableUse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TableService {

    private static final long DEFAULT_EXPIRE_TIME = 2 * 60 * 60 * 1000; //默认超时时间2个小时

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private TableUseMapper tableUseMapper;

    @Autowired
    private AccessTokenService accessTokenService;

    /**
     * 开桌
     * @param uid
     * @return
     */
    public String openTable(String uid){
        if(StringUtils.isBlank(uid))
            throw CommonException.PARAMS_NOT_NULL.format("桌子id");

        Table table = tableMapper.selectByUid(uid);
        if(table == null)
            throw TableException.TABLE_NOT_EXIST;

        String token = accessTokenService.genAccessToken();
        bindToken(token,uid);

        // 插入桌子使用记录
        TableUse tableUse = new TableUse();
        tableUse.setTableUid(uid);
        tableUse.setAccessToken(token);
        tableUse.setCreateTime(new Date());
        tableUse.setStatus(TableUserStatusEnum.ORDERING.getCode());
        tableUseMapper.insertSelective(tableUse);

        return token;
    }

    /**
     * 桌子与token进行绑定
     * @param accessToken
     * @param uid
     */
    private void bindToken(String accessToken, String uid){
        if(StringUtils.isBlank(accessToken) || StringUtils.isBlank(uid))
            return;

        redisUtil.set(accessToken,uid, DEFAULT_EXPIRE_TIME);
    }
}
