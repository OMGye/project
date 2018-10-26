package com.vo;

/**
 * Created by upupgogogo on 2018/10/26.上午12:04
 */
public class RecordDecNum {

    private String recordDec;

    private Integer number;

    public RecordDecNum() {
    }

    public RecordDecNum(String recordDec, Integer number) {

        this.recordDec = recordDec;
        this.number = number;
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
