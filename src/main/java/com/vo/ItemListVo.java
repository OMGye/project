package com.vo;

import java.util.Date;

/**
 * Created by upupgogogo on 2018/8/2.上午4:48
 */
public class ItemListVo {

    private Integer itemId;
    private String itemName;
    private Date createTime;
    private Date endTime;

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getItemId() {

        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
