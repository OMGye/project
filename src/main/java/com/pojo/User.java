package com.pojo;

import java.util.Date;

public class User {
    private Integer userId;

    private String userName;

    private String password;

    private String accountInfoCode;

    private String phone;

    private Byte state;

    private Integer userType;

    private Integer itemId;

    private Date createTime;

    private Date lastEditTime;

    private String userImg;

    public User(Integer userId, String userName, String password, String accountInfoCode, String phone, Byte state, Integer userType, Integer itemId, Date createTime, Date lastEditTime, String userImg) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.accountInfoCode = accountInfoCode;
        this.phone = phone;
        this.state = state;
        this.userType = userType;
        this.itemId = itemId;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.userImg = userImg;
    }

    public void setAccountInfoCode(String accountInfoCode) {
        this.accountInfoCode = accountInfoCode;
    }

    public String getAccountInfoCode() {

        return accountInfoCode;
    }

    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg == null ? null : userImg.trim();
    }
}