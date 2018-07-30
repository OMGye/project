package com.dao;

import com.pojo.MaterialUseInfo;

public interface MaterialUseInfoMapper {
    int deleteByPrimaryKey(Integer materialUseId);

    int insert(MaterialUseInfo record);

    int insertSelective(MaterialUseInfo record);

    MaterialUseInfo selectByPrimaryKey(Integer materialUseId);

    int updateByPrimaryKeySelective(MaterialUseInfo record);

    int updateByPrimaryKey(MaterialUseInfo record);
}