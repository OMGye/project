package com.pojo;

import java.util.Date;

public class MaterialUseInfo {
    private Integer materialUseId;

    private String categoryName;

    private String itemName;

    private String userName;

    private Integer number;

    private Integer state;

    private String checkUserName;

    private Date createTime;

    private Date lastEditTime;

    public MaterialUseInfo(Integer materialUseId, String categoryName, String itemName, String userName, Integer number, Integer state, String checkUserName, Date createTime, Date lastEditTime) {
        this.materialUseId = materialUseId;
        this.categoryName = categoryName;
        this.itemName = itemName;
        this.userName = userName;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
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