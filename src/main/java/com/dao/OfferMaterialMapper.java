package com.dao;

import com.pojo.OfferMaterial;

public interface OfferMaterialMapper {
    int deleteByPrimaryKey(Integer offerId);

    int insert(OfferMaterial record);

    int insertSelective(OfferMaterial record);

    OfferMaterial selectByPrimaryKey(Integer offerId);

    int updateByPrimaryKeySelective(OfferMaterial record);

    int updateByPrimaryKey(OfferMaterial record);
}