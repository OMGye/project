package com.vo;

import java.util.List;

/**
 * Created by upupgogogo on 2018/10/27.下午3:52
 */
public class IndexVo {

    private List<ItemIndexVo> itemIndexVoList;

    private List<OfferMaterialIndexVo> offerMaterialIndexVoList;

    public void setItemIndexVoList(List<ItemIndexVo> itemIndexVoList) {
        this.itemIndexVoList = itemIndexVoList;
    }

    public void setOfferMaterialIndexVoList(List<OfferMaterialIndexVo> offerMaterialIndexVoList) {
        this.offerMaterialIndexVoList = offerMaterialIndexVoList;
    }

    public List<ItemIndexVo> getItemIndexVoList() {

        return itemIndexVoList;
    }

    public List<OfferMaterialIndexVo> getOfferMaterialIndexVoList() {
        return offerMaterialIndexVoList;
    }
}
