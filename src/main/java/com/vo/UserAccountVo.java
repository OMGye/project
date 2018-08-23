package com.vo;

import java.math.BigDecimal;

/**
 * Created by upupgogogo on 2018/8/23.上午1:51
 */
public class UserAccountVo {

    private BigDecimal dayPayAccount;

    private BigDecimal dayIncomeAccount;

    private BigDecimal monthPayAccount;

    private BigDecimal monthIncomeAccount;

    public void setDayPayAccount(BigDecimal dayPayAccount) {
        this.dayPayAccount = dayPayAccount;
    }

    public void setDayIncomeAccount(BigDecimal dayIncomeAccount) {
        this.dayIncomeAccount = dayIncomeAccount;
    }

    public void setMonthPayAccount(BigDecimal monthPayAccount) {
        this.monthPayAccount = monthPayAccount;
    }

    public void setMonthIncomeAccount(BigDecimal monthIncomeAccount) {
        this.monthIncomeAccount = monthIncomeAccount;
    }

    public BigDecimal getDayPayAccount() {

        return dayPayAccount;
    }

    public BigDecimal getDayIncomeAccount() {
        return dayIncomeAccount;
    }

    public BigDecimal getMonthPayAccount() {
        return monthPayAccount;
    }

    public BigDecimal getMonthIncomeAccount() {
        return monthIncomeAccount;
    }
}