package com.sss.restaurant.table.service;

import com.sss.restaurant.common.redis.RedisUtil;
import com.sss.restaurant.common.service.AccessTokenService;
import com.sss.restaurant.common.utils.GenNumberUtil;
import com.sss.restaurant.exception.CommonException;
import com.sss.restaurant.exception.TableException;
import com.sss.restaurant.table.dao.TableMapper;
import com.sss.restaurant.table.model.Table;
import com.sss.restaurant.user.dao.UserMapper;
import com.sss.restaurant.user.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TableService {

    private static final long DEFAULT_EXPIRE_TIME = 4 * 60 * 60 * 1000; //默认超时时间4个小时

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 开桌
     * @param deviceId 设备id
     * @param tableUid 桌子uid
     * @return
     */
    public String openTable(String deviceId, String tableUid){
        if(StringUtils.isBlank(deviceId) || StringUtils.isBlank(tableUid))
            throw CommonException.PARAMS_NOT_NULL.format("桌子id,用户设备id");

        Table table = tableMapper.selectByUid(tableUid);
        if(table == null)
            throw TableException.TABLE_NOT_EXIST;

        // 生成用户信息
        User user = genUser(deviceId);

        String token = accessTokenService.genAccessToken();
        bindToken(token, deviceId, user.getUid(), tableUid);

        return token;
    }

    /**
     * 桌子与token进行绑定
     * @param accessToken
     * @param uid
     */
    private void bindToken(String accessToken, String deviceId, String uid, String tableUid){
        if(StringUtils.isBlank(accessToken) || StringUtils.isBlank(uid) || StringUtils.isBlank(tableUid))
            return;

        //由设备id_用户id_桌子id作为值
        redisUtil.set(accessToken,deviceId + "_" + uid + "_" + tableUid, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 生成用户
     * @param deviceId
     * @return
     */
    private User genUser(String deviceId){
        User user = userMapper.selectByDeviceId(deviceId);
        if(user != null) return user;

        user = new User();
        user.setDeviceId(deviceId);
        user.setUid(GenNumberUtil.genUuidNoLine());
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);
        return user;
    }
}
