package com.dao;

import com.pojo.AccountInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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

    List<AccountInfo> selectCheckListNotItemId(Integer userId);

    List<AccountInfo> selectItemAccountList(Integer itemId);

    List<AccountInfo> selectAccountList();

    int selectCountByUserIdCheck(Integer userId);

    int selectCountByUserIdUncheck(String checkUserName);

    BigDecimal selectAllPayAccountDay();

    BigDecimal selectAllIncomeAccountDay();

    BigDecimal selectAllPayAccountMonth();

    BigDecimal selectAllIncomeAccountMonth();


    BigDecimal selectAllPayAccountDayByItemId(Integer itemId);

    BigDecimal selectAllIncomeAccountDayByItemId(Integer itemId);

    BigDecimal selectAllPayAccountMonthByItemId(Integer itemId);

    BigDecimal selectAllIncomeAccountMonthByItemId(Integer itemId);


    BigDecimal selectAllPayAccountDayByUserId(Integer userId);

    BigDecimal selectAllIncomeAccountDayByUserId(Integer userId);

    BigDecimal selectAllPayAccountMonthByUserId(Integer userId);

    BigDecimal selectAllIncomeAccountMonthByUserId(Integer userId);


    BigDecimal selectAllPayAccountDayCompany();

    BigDecimal selectAllIncomeAccountDayCompany();

    BigDecimal selectAllPayAccountMonthCompany();

    BigDecimal selectAllIncomeAccountMonthCompany();

    List<AccountInfo> selectByTime(@Param("startTime") String startTime, @Param("endTime")String endTime);

}