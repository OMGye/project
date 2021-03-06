package com.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by upupgogogo on 2018/8/29.下午11:21
 */
public class UserPersonInfoVo {

    private Integer userId;

    private String userName;

    private String password;

    private String accountInfoCode;

    private String phone;

    private Byte state;

    private Integer userType;

    private List<ItemIndexVo> list;

    private Date createTime;

    private Date lastEditTime;

    private String userImg;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountInfoCode(String accountInfoCode) {
        this.accountInfoCode = accountInfoCode;
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



    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
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

    public String getAccountInfoCode() {
        return accountInfoCode;
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

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setList(List<ItemIndexVo> list) {
        this.list = list;
    }

    public List<ItemIndexVo> getList() {

        return list;
    }
}
