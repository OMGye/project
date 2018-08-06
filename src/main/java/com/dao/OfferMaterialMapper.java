package com.dao;

import com.pojo.OfferMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OfferMaterialMapper {
    int deleteByPrimaryKey(Integer offerId);

    int insert(OfferMaterial record);

    int insertSelective(OfferMaterial record);

    OfferMaterial selectByPrimaryKey(Integer offerId);

    int updateByPrimaryKeySelective(OfferMaterial record);

    int updateByPrimaryKey(OfferMaterial record);

    int selectCountByName(String offerCompany);

    int selectCountByNameAndId(@Param("offerCompany") String offerCompany, @Param("offerId") Integer offerId);

    List<OfferMaterial> selectList();
}