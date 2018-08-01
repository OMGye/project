package com.pojo;

import java.util.Date;

public class OfferMaterial {
    private Integer offerId;

    private String offerCompany;

    private String offerPhone;

    private String address;

    private Date createTime;

    private Date lastEditTime;

    public OfferMaterial(Integer offerId, String offerCompany, String offerPhone, String address, Date createTime, Date lastEditTime) {
        this.offerId = offerId;
        this.offerCompany = offerCompany;
        this.offerPhone = offerPhone;
        this.address = address;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    public OfferMaterial() {
        super();
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getOfferCompany() {
        return offerCompany;
    }

    public void setOfferCompany(String offerCompany) {
        this.offerCompany = offerCompany == null ? null : offerCompany.trim();
    }

    public String getOfferPhone() {
        return offerPhone;
    }

    public void setOfferPhone(String offerPhone) {
        this.offerPhone = offerPhone == null ? null : offerPhone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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