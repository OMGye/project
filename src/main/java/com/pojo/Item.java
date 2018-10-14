package com.pojo;

import java.util.Date;

public class Item {
    private Integer itemId;

    private String itemName;

    private String itemDec;

    private Integer prority;

    private Integer itemManagerId;

    private String itemManagerName;

    private Integer itemUploaderId;

    private String itemUploaderName;

    private String itemFile;

    private String itemFileName;

    private Integer state;

    private Date createTime;

    private Date lastEditTime;

    private Date endTime;

    public Item(Integer itemId, String itemName, String itemDec, Integer prority, Integer itemManagerId, String itemManagerName, Integer itemUploaderId, String itemUploaderName, String itemFile, String itemFileName, Integer state, Date createTime, Date lastEditTime, Date endTime) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDec = itemDec;
        this.prority = prority;
        this.itemManagerId = itemManagerId;
        this.itemManagerName = itemManagerName;
        this.itemUploaderId = itemUploaderId;
        this.itemUploaderName = itemUploaderName;
        this.itemFile = itemFile;
        this.itemFileName = itemFileName;
        this.state = state;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
        this.endTime = endTime;
    }

    public Item() {
        super();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getItemDec() {
        return itemDec;
    }

    public void setItemDec(String itemDec) {
        this.itemDec = itemDec == null ? null : itemDec.trim();
    }

    public Integer getPrority() {
        return prority;
    }

    public void setPrority(Integer prority) {
        this.prority = prority;
    }

    public Integer getItemManagerId() {
        return itemManagerId;
    }

    public void setItemManagerId(Integer itemManagerId) {
        this.itemManagerId = itemManagerId;
    }

    public String getItemManagerName() {
        return itemManagerName;
    }

    public void setItemManagerName(String itemManagerName) {
        this.itemManagerName = itemManagerName == null ? null : itemManagerName.trim();
    }

    public Integer getItemUploaderId() {
        return itemUploaderId;
    }

    public void setItemUploaderId(Integer itemUploaderId) {
        this.itemUploaderId = itemUploaderId;
    }

    public String getItemUploaderName() {
        return itemUploaderName;
    }

    public void setItemUploaderName(String itemUploaderName) {
        this.itemUploaderName = itemUploaderName == null ? null : itemUploaderName.trim();
    }

    public String getItemFile() {
        return itemFile;
    }

    public void setItemFile(String itemFile) {
        this.itemFile = itemFile == null ? null : itemFile.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setItemFileName(String itemFileName) {
        this.itemFileName = itemFileName;
    }

    public String getItemFileName() {

        return itemFileName;
    }
}