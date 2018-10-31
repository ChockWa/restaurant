package com.sss.restaurant.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sss.restaurant.user.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据uid查询用户信息
     * @param uid
     * @return
     */
    User selectByUid(String uid);

    /**
     * 根据设备id查询用户信息
     * @param deviceId
     * @return
     */
    User selectByDeviceId(String deviceId);
}