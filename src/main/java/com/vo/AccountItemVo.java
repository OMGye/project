package com.vo;

import java.math.BigDecimal;

/**
 * Created by upupgogogo on 2018/8/14.下午10:53
 */
public class AccountItemVo {

    private Integer itemId;

    private String itemName;

    private BigDecimal payAccount;

    private BigDecimal incomeAccount;

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPayAccount(BigDecimal payAccount) {
        this.payAccount = payAccount;
    }

    public void setIncomeAccount(BigDecimal incomeAccount) {
        this.incomeAccount = incomeAccount;
    }

    public Integer getItemId() {

        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getPayAccount() {
        return payAccount;
    }

    public BigDecimal getIncomeAccount() {
        return incomeAccount;
    }
}
