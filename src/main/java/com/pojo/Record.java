package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Record {
    private Integer recordId;

    private Integer userId;

    private String userName;

    private Integer itemId;

    private String itemName;

    private Integer recordType;

    private String recordDec;

    private Integer offerId;

    private BigDecimal unitPrice;

    private Integer number;

    private Integer state;

    private String recordImgs;

    private Date createTime;

    private Date lastEditTime;

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public void setRecordDec(String recordDec) {
        this.recordDec = recordDec;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setRecordImgs(String recordImgs) {
        this.recordImgs = recordImgs;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getRecordId() {

        return recordId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public String getRecordDec() {
        return recordDec;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getState() {
        return state;
    }

    public String getRecordImgs() {
        return recordImgs;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public Record(Integer recordId, Integer userId, String userName, Integer itemId, String itemName, Integer recordType, String recordDec, Integer offerId, BigDecimal unitPrice, Integer number, Integer state, String recordImgs, Date createTime, Date lastEditTime) {

        this.recordId = recordId;
        this.userId = userId;
        this.userName = userName;
        this.itemId = itemId;
        this.itemName = itemName;
        this.recordType = recordType;
        this.recordDec = recordDec;
        this.offerId = offerId;
        this.unitPrice = unitPrice;
        this.number = number;
        this.state = state;
        this.recordImgs = recordImgs;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    public Record() {
    }
}