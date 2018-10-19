package com.sss.restaurant.table.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sss.restaurant.table.model.TableUse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TableUseMapper extends BaseMapper<TableUse>{
    int deleteByPrimaryKey(Long id);

    int insert(TableUse record);

    int insertSelective(TableUse record);

    TableUse selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TableUse record);

    int updateByPrimaryKey(TableUse record);
}