package com.vo;

/**
 * Created by upupgogogo on 2018/8/3.下午9:03
 */
public class UserVo {
    private Integer userId;
    private String userName;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {

        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public UserVo() {
    }

    public UserVo(Integer userId, String userName) {

        this.userId = userId;
        this.userName = userName;
    }
}
