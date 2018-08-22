package com.dao;

import com.pojo.MaterialBuyInfo;

import java.util.List;

public interface MaterialBuyInfoMapper {
    int deleteByPrimaryKey(Integer materialInfoId);

    int insert(MaterialBuyInfo record);

    int insertSelective(MaterialBuyInfo record);

    MaterialBuyInfo selectByPrimaryKey(Integer materialInfoId);

    int updateByPrimaryKeySelective(MaterialBuyInfo record);

    int updateByPrimaryKey(MaterialBuyInfo record);

    int updateByOfferId(Integer offerId);

    List<MaterialBuyInfo> selectByItemId(Integer itemId);

    int count(Integer itemId);
}