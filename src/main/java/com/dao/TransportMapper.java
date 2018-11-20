package com.dao;

import com.pojo.OfferMaterial;
import com.pojo.Transport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransportMapper {
    int deleteByPrimaryKey(Integer transportId);

    int insert(Transport record);

    int insertSelective(Transport record);

    Transport selectByPrimaryKey(Integer transportId);

    int updateByPrimaryKeySelective(Transport record);

    int updateByPrimaryKey(Transport record);

    int selectCountByName(String transportName);

    List<Transport> selectList();

    int selectCountByNameAndId(@Param("transportName") String transportName, @Param("transportId") Integer transportId);

    List<Transport> selectByTransportName(String transportName);
}