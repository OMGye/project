package com.vo;

import java.math.BigDecimal;

/**
 * Created by upupgogogo on 2018/8/23.上午1:51
 */
public class UserAccountVo {

    private BigDecimal oneDayPayAccount;

    private BigDecimal oneDayIncomeAccount;

    private BigDecimal monthPayAccount;

    private BigDecimal monthDayIncomeAccount;

    public void setOneDayPayAccount(BigDecimal oneDayPayAccount) {
        this.oneDayPayAccount = oneDayPayAccount;
    }

    public void setOneDayIncomeAccount(BigDecimal oneDayIncomeAccount) {
        this.oneDayIncomeAccount = oneDayIncomeAccount;
    }

    public void setMonthPayAccount(BigDecimal monthPayAccount) {
        this.monthPayAccount = monthPayAccount;
    }

    public void setMonthDayIncomeAccount(BigDecimal monthDayIncomeAccount) {
        this.monthDayIncomeAccount = monthDayIncomeAccount;
    }

    public BigDecimal getOneDayPayAccount() {

        return oneDayPayAccount;
    }

    public BigDecimal getOneDayIncomeAccount() {
        return oneDayIncomeAccount;
    }

    public BigDecimal getMonthPayAccount() {
        return monthPayAccount;
    }

    public BigDecimal getMonthDayIncomeAccount() {
        return monthDayIncomeAccount;
    }
}
