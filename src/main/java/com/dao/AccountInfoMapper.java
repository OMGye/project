package com.dao;

import com.pojo.AccountInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountInfoMapper {
    int deleteByPrimaryKey(Integer accountInfoId);

    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);

    AccountInfo selectByPrimaryKey(Integer accountInfoId);

    int updateByPrimaryKeySelective(AccountInfo record);

    int updateByPrimaryKey(AccountInfo record);

    List<AccountInfo> selectUncheckListNotItemId();

    List<AccountInfo> selectUncheckList(Integer itemId);

    List<AccountInfo> selectCheckList(@Param("itemId") Integer itemId, @Param("userId")Integer userId);
}