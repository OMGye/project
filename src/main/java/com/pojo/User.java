package com.pojo;

import java.util.Date;

public class User {
    private Integer userId;

    private String userName;

    private String password;

    private String userImg;

    private String phone;

    private Byte state;

    private Integer userType;

    private String itemId;

    private Date createTime;

    private Date lastEditTime;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getUserId() {

        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserImg() {
        return userImg;
    }

    public String getPhone() {
        return phone;
    }

    public Byte getState() {
        return state;
    }

    public Integer getUserType() {
        return userType;
    }

    public String getItemId() {
        return itemId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public User() {

    }

    public User(Integer userId, String userName, String password, String userImg, String phone, Byte state, Integer userType, String itemId, Date createTime, Date lastEditTime) {

        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userImg = userImg;
        this.phone = phone;
        this.state = state;
        this.userType = userType;
        this.itemId = itemId;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }
}