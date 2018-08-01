package com.pojo;

import java.util.Date;

public class MaterialUseInfo {
    private Integer materialUseId;

    private String categoryName;

    private Integer itemId;

    private Integer userId;

    private Integer number;

    private Integer state;

    private String checkUserName;

    private Date createTime;

    private Date lastEditTime;

    public MaterialUseInfo(Integer materialUseId, String categoryName, Integer itemId, Integer userId, Integer number, Integer state, String checkUserName, Date createTime, Date lastEditTime) {
        this.materialUseId = materialUseId;
        this.categoryName = categoryName;
        this.itemId = itemId;
        this.userId = userId;
        this.number = number;
        this.state = state;
        this.checkUserName = checkUserName;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    public MaterialUseInfo() {
        super();
    }

    public Integer getMaterialUseId() {
        return materialUseId;
    }

    public void setMaterialUseId(Integer materialUseId) {
        this.materialUseId = materialUseId;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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