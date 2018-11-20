package com.pojo;

import java.util.Date;

public class Transport {
    private Integer transportId;

    private String transportName;

    private String transportNumber;

    private String transportPhone;

    private Date createTime;

    private Date updateTime;

    public Transport(Integer transportId, String transportName, String transportNumber, String transportPhone, Date createTime, Date updateTime) {
        this.transportId = transportId;
        this.transportName = transportName;
        this.transportNumber = transportNumber;
        this.transportPhone = transportPhone;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Transport() {
        super();
    }

    public Integer getTransportId() {
        return transportId;
    }

    public void setTransportId(Integer transportId) {
        this.transportId = transportId;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName == null ? null : transportName.trim();
    }

    public String getTransportNumber() {
        return transportNumber;
    }

    public void setTransportNumber(String transportNumber) {
        this.transportNumber = transportNumber == null ? null : transportNumber.trim();
    }

    public String getTransportPhone() {
        return transportPhone;
    }

    public void setTransportPhone(String transportPhone) {
        this.transportPhone = transportPhone == null ? null : transportPhone.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}