package com.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class MaterialStock {
    private Integer materialStockId;

    private String categoryName;

    private Integer itemId;

    private Integer sellStock;

    private Integer useStock;

    private BigDecimal unitPrice;

    private Date createTime;

    private Date lastEditTime;

    public MaterialStock(Integer materialStockId, String categoryName, Integer itemId, Integer sellStock, Integer useStock, BigDecimal unitPrice, Date createTime, Date lastEditTime) {
        this.materialStockId = materialStockId;
        this.categoryName = categoryName;
        this.itemId = itemId;
        this.sellStock = sellStock;
        this.useStock = useStock;
        this.unitPrice = unitPrice;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }

    public MaterialStock() {
        super();
    }

    public Integer getMaterialStockId() {
        return materialStockId;
    }

    public void setMaterialStockId(Integer materialStockId) {
        this.materialStockId = materialStockId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getSellStock() {
        return sellStock;
    }

    public void setSellStock(Integer sellStock) {
        this.sellStock = sellStock;
    }

    public Integer getUseStock() {
        return useStock;
    }

    public void setUseStock(Integer useStock) {
        this.useStock = useStock;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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
}