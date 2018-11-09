package com.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.vo.ItemIndexVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public Category() {
    }


    public static void main(String[] args) {
        List<ItemIndexVo> list = new ArrayList<>();
        list.add(new ItemIndexVo(1,"projectOne"));
        list.add(new ItemIndexVo(2,"projectTwo"));
        list.add(new ItemIndexVo(3,"projectThree"));
        String pojo = JSONObject.toJSONString(list);
        System.out.println(pojo);
        Gson gson = new Gson();
        List<ItemIndexVo> newList = gson.fromJson(pojo, new TypeToken<List<ItemIndexVo>>(){}.
                getType());

        for (ItemIndexVo itemIndexVo : newList)
            System.out.println(itemIndexVo.getItemId() + "--" + itemIndexVo.getItemName());


    }
}