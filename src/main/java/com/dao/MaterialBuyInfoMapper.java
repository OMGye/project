package com.dao;

import com.pojo.MaterialBuyInfo;

public interface MaterialBuyInfoMapper {
    int deleteByPrimaryKey(Integer materialInfoId);

    int insert(MaterialBuyInfo record);

    int insertSelective(MaterialBuyInfo record);

    MaterialBuyInfo selectByPrimaryKey(Integer materialInfoId);

    int updateByPrimaryKeySelective(MaterialBuyInfo record);

    int updateByPrimaryKey(MaterialBuyInfo record);
}