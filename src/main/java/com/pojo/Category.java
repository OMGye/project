package com.pojo;

import java.util.Date;

public class Category {
    private Integer categoryId;

    private String categoryName;

    private Integer priority;

    private String specifications;

    private String unit;

    private Date createTime;

    private Date lastEditTime;

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getCategoryId() {

        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getSpecifications() {
        return specifications;
    }

    public String getUnit() {
        return unit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public Category(Integer categoryId, String categoryName, Integer priority, String specifications, String unit, Date createTime, Date lastEditTime) {

        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.priority = priority;
        this.specifications = specifications;
        this.unit = unit;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }
}