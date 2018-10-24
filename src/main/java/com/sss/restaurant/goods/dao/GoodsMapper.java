package com.sss.restaurant.goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sss.restaurant.goods.dto.CategoryGoodsDto;
import com.sss.restaurant.goods.model.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods>{
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    /**
     * 获取分类商品列表
     * @param params
     * @return
     */
    List<CategoryGoodsDto> getCategoryGoodsList(Map<String,Object> params);
}