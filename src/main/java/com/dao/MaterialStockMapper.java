package com.dao;

import com.pojo.MaterialStock;

public interface MaterialStockMapper {
    int deleteByPrimaryKey(Integer materialStockId);

    int insert(MaterialStock record);

    int insertSelective(MaterialStock record);

    MaterialStock selectByPrimaryKey(Integer materialStockId);

    int updateByPrimaryKeySelective(MaterialStock record);

    int updateByPrimaryKey(MaterialStock record);
}