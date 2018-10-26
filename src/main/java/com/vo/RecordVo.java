package com.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by upupgogogo on 2018/10/1.上午10:05
 */
public class RecordVo {

    private Integer recordId;

    private Integer userId;

    private String userName;

    private Integer itemId;

    private String itemName;

    private Integer recordType;

    private String recordCarOffer;

    private Integer recordCarNumber;

    private String recordDec;

    private Integer offerId;

    private BigDecimal unitPrice;

    private Integer number;

    private BigDecimal sumPrice;

    private Integer state;

    private String recordImgs;

    private String offerName;

    private String recordImgName;

    public void setRecordImgName(String recordImgName) {
        this.recordImgName = recordImgName;
    }

    public String getRecordImgName() {

        return recordImgName;
    }

    public void setRecordRefuse(String recordRefuse) {
        this.recordRefuse = recordRefuse;
    }

    public String getRecordRefuse() {

        return recordRefuse;
    }

    private String recordRefuse;

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

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setRecordImgs(String recordImgs) {
        this.recordImgs = recordImgs;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
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

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public Integer getState() {
        return state;
    }

    public String getRecordImgs() {
        return recordImgs;
    }

    public String getOfferName() {
        return offerName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setRecordCarOffer(String recordCarOffer) {
        this.recordCarOffer = recordCarOffer;
    }

    public void setRecordCarNumber(Integer recordCarNumber) {
        this.recordCarNumber = recordCarNumber;
    }

    public String getRecordCarOffer() {

        return recordCarOffer;
    }

    public Integer getRecordCarNumber() {
        return recordCarNumber;
    }
}
