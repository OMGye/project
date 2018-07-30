package com.dao;

import com.pojo.AccountInfo;

public interface AccountInfoMapper {
    int deleteByPrimaryKey(Integer accountInfoId);

    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);

    AccountInfo selectByPrimaryKey(Integer accountInfoId);

    int updateByPrimaryKeySelective(AccountInfo record);

    int updateByPrimaryKey(AccountInfo record);
}