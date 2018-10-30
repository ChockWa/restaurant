package com.sss.restaurant.table.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sss.restaurant.table.model.Table;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TableMapper extends BaseMapper<Table>{
    int deleteByPrimaryKey(Long id);

    int insert(Table record);

    int insertSelective(Table record);

    Table selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Table record);

    int updateByPrimaryKey(Table record);

    /**
     * 通过uid获取table信息
     * @param uid
     * @return
     */
    Table selectByUid(String uid);
}