package com.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by upupgogogo on 2018/10/25.下午11:34
 */
public class RecordAmountVo {

    private BigDecimal sumPrice;

    private BigDecimal materailSumPrice;

    public void setMaterailSumPrice(BigDecimal materailSumPrice) {
        this.materailSumPrice = materailSumPrice;
    }

    public BigDecimal getMaterailSumPrice() {

        return materailSumPrice;
    }

    private List<RecordDecNum> list;

    public void setSumPrice(BigDecimal sumPrice) {
        this.sumPrice = sumPrice;
    }

    public void setList(List<RecordDecNum> list) {
        this.list = list;
    }

    public BigDecimal getSumPrice() {
        return sumPrice;
    }

    public List<RecordDecNum> getList() {
        return list;
    }
}
