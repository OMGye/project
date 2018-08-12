package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class AccountInfo {
    private Integer accountInfoId;

    private String categoryName;

    private Integer userId;

    private Integer itemId;

    private String accountInfoCode;

    private String accountImg;

    private BigDecimal prePrice;

    private BigDecimal accountRelPrice;

    private String accountInfoDec;

    private Integer state;

    private String checkUserName;

    private Date createTime;

    private Date lastEditTime;

    public AccountInfo(Integer accountInfoId, String categoryName, Integer userId, Integer itemId, String accountInfoCode, String accountImg, BigDecimal prePrice, BigDecimal accountRelPrice, String accountInfoDec, Integer state, String checkUserName, Date createTime, Date lastEditTime) {
        this.accountInfoId = accountInfoId;
        this.categoryName = categoryName;
        this.userId = userId;
        this.itemId = itemId;
        this.accountInfoCode = accountInfoCode;
        this.accountImg = accountImg;
        this.prePrice = prePrice;
        this.accountRelPrice = accountRelPrice;
        this.accountInfoDec = accountInfoDec;
        this.state = state;
        this.checkUserName = checkUserName;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    public String getAccountInfoCode() {
        return accountInfoCode;
    }

    public void setAccountInfoCode(String accountInfoCode) {
        this.accountInfoCode = accountInfoCode;
    }

    public AccountInfo() {
        super();
    }

    public Integer getAccountInfoId() {
        return accountInfoId;
    }

    public void setAccountInfoId(Integer accountInfoId) {
        this.accountInfoId = accountInfoId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getAccountImg() {
        return accountImg;
    }

    public void setAccountImg(String accountImg) {
        this.accountImg = accountImg == null ? null : accountImg.trim();
    }

    public BigDecimal getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(BigDecimal prePrice) {
        this.prePrice = prePrice;
    }

    public BigDecimal getAccountRelPrice() {
        return accountRelPrice;
    }

    public void setAccountRelPrice(BigDecimal accountRelPrice) {
        this.accountRelPrice = accountRelPrice;
    }

    public String getAccountInfoDec() {
        return accountInfoDec;
    }

    public void setAccountInfoDec(String accountInfoDec) {
        this.accountInfoDec = accountInfoDec == null ? null : accountInfoDec.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName == null ? null : checkUserName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}