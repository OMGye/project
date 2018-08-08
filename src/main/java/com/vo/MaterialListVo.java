package com.vo;

import java.util.Date;

/**
 * Created by upupgogogo on 2018/8/8.下午2:37
 */
public class MaterialListVo {

    private Integer id;

    private Integer type;

    private Integer itemId;

    private Integer userId;

    private String categoryName;

    private String checkUserName;

    private Integer number;

    private Date createTime;

    private Date lastEditTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getId() {

        return id;
    }

    public Integer getType() {
        return type;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public Integer getNumber() {
        return number;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }
}
