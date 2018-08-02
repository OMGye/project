package com.pojo;

import java.util.Date;

public class Item {
    private Integer itemId;

    private String itemName;

    private String itemDec;

    private Integer prority;

    private Integer userId;

    private Integer state;

    private Date createTime;

    private Date lastEditTime;

    private Date endTime;

    public Item(Integer itemId, String itemName, String itemDec, Integer prority, Integer userId, Integer state, Date createTime, Date lastEditTime, Date endTime) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDec = itemDec;
        this.prority = prority;
        this.userId = userId;
        this.state = state;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.endTime = endTime;
    }

    public Item() {
        super();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getItemDec() {
        return itemDec;
    }

    public void setItemDec(String itemDec) {
        this.itemDec = itemDec == null ? null : itemDec.trim();
    }

    public Integer getPrority() {
        return prority;
    }

    public void setPrority(Integer prority) {
        this.prority = prority;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}