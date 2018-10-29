package com.vo;

import java.math.BigDecimal;

/**
 * Created by upupgogogo on 2018/10/26.上午12:04
 */
public class RecordDecNum {

    private String recordDec;

    private Integer number;

    private BigDecimal totalPrice;

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() {

        return totalPrice;
    }

    public RecordDecNum() {
    }

    public RecordDecNum(String recordDec, Integer number, BigDecimal totalPrice) {
        this.recordDec = recordDec;
        this.number = number;
        this.totalPrice = totalPrice;
    }

    public void setRecordDec(String recordDec) {

        this.recordDec = recordDec;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRecordDec() {

        return recordDec;
    }

    public Integer getNumber() {
        return number;
    }
}
