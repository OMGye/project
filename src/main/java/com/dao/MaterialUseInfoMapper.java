package com.dao;

import com.pojo.MaterialBuyInfo;
import com.pojo.MaterialUseInfo;

import java.util.List;

public interface MaterialUseInfoMapper {
    int deleteByPrimaryKey(Integer materialUseId);

    int insert(MaterialUseInfo record);

    int insertSelective(MaterialUseInfo record);

    MaterialUseInfo selectByPrimaryKey(Integer materialUseId);

    int updateByPrimaryKeySelective(MaterialUseInfo record);

    int updateByPrimaryKey(MaterialUseInfo record);

    List<MaterialUseInfo> selectByItemId(Integer itemId);
}