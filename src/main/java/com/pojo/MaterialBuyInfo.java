package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class MaterialBuyInfo {
    private Integer materialInfoId;

    private String categoryName;

    private Integer itemId;

    private Integer userId;

    private Integer accountInfoId;

    private Integer offerId;

    private String materialInfoImg;

    private String sellerName;

    private BigDecimal unitPrice;

    private Integer number;

    private BigDecimal totalPrice;

    private Integer state;

    private String checkUserName;

    private Date createTime;

    private Date lastEditTime;

    public MaterialBuyInfo(Integer materialInfoId, String categoryName, Integer itemId, Integer userId, Integer accountInfoId, Integer offerId, String materialInfoImg, String sellerName, BigDecimal unitPrice, Integer number, BigDecimal totalPrice, Integer state, String checkUserName, Date createTime, Date lastEditTime) {
        this.materialInfoId = materialInfoId;
        this.categoryName = categoryName;
        this.itemId = itemId;
        this.userId = userId;
        this.accountInfoId = accountInfoId;
        this.offerId = offerId;
        this.materialInfoImg = materialInfoImg;
        this.sellerName = sellerName;
        this.unitPrice = unitPrice;
        this.number = number;
        this.totalPrice = totalPrice;
        this.state = state;
        this.checkUserName = checkUserName;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    public MaterialBuyInfo() {
        super();
    }

    public Integer getMaterialInfoId() {
        return materialInfoId;
    }

    public void setMaterialInfoId(Integer materialInfoId) {
        this.materialInfoId = materialInfoId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccountInfoId() {
        return accountInfoId;
    }

    public void setAccountInfoId(Integer accountInfoId) {
        this.accountInfoId = accountInfoId;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getMaterialInfoImg() {
        return materialInfoImg;
    }

    public void setMaterialInfoImg(String materialInfoImg) {
        this.materialInfoImg = materialInfoImg == null ? null : materialInfoImg.trim();
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName == null ? null : sellerName.trim();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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