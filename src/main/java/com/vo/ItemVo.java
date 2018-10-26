package com.vo;

import java.util.Date;

/**
 * Created by upupgogogo on 2018/8/23.下午5:25
 */
public class ItemVo {

    private Integer itemId;

    private String itemName;

    private String itemDec;

    private Integer prority;

    private Integer userId;

    private Integer state;

    private Date createTime;

    private Date lastEditTime;

    private Date endTime;

    private String accountUser;

    private String accountCheckUser;

    private String materialUser;

    private String materialCheckUser;

    private Integer accountUserId;

    private Integer accountCheckUserId;

    private Integer materialUserId;

    private Integer materialCheckUserId;

    public void setAccountUserId(Integer accountUserId) {
        this.accountUserId = accountUserId;
    }

    public void setAccountCheckUserId(Integer accountCheckUserId) {
        this.accountCheckUserId = accountCheckUserId;
    }

    public void setMaterialUserId(Integer materialUserId) {
        this.materialUserId = materialUserId;
    }

    public void setMaterialCheckUserId(Integer materialCheckUserId) {
        this.materialCheckUserId = materialCheckUserId;
    }

    public Integer getAccountUserId() {
        return accountUserId;
    }

    public Integer getAccountCheckUserId() {
        return accountCheckUserId;
    }

    public Integer getMaterialUserId() {
        return materialUserId;
    }

    public Integer getMaterialCheckUserId() {
        return materialCheckUserId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDec(String itemDec) {
        this.itemDec = itemDec;
    }

    public void setPrority(Integer prority) {
        this.prority = prority;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setAccountUser(String accountUser) {
        this.accountUser = accountUser;
    }

    public void setAccountCheckUser(String accountCheckUser) {
        this.accountCheckUser = accountCheckUser;
    }

    public void setMaterialUser(String materialUser) {
        this.materialUser = materialUser;
    }

    public void setMaterialCheckUser(String materialCheckUser) {
        this.materialCheckUser = materialCheckUser;
    }

    public Integer getItemId() {

        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDec() {
        return itemDec;
    }

    public Integer getPrority() {
        return prority;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getState() {
        return state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getAccountUser() {
        return accountUser;
    }

    public String getAccountCheckUser() {
        return accountCheckUser;
    }

    public String getMaterialUser() {
        return materialUser;
    }

    public String getMaterialCheckUser() {
        return materialCheckUser;
    }


}
