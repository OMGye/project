package com.dao;

import com.pojo.Record;
import com.vo.RecordDecNum;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RecordMapper {
    int deleteByPrimaryKey(Integer recordId);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);

    List<Record> selectlist(@Param("state")Integer state, @Param("type")Integer type, @Param("itemList")List<Integer> itemList,@Param("userId")Integer userId);

    List<Record> selectlistAll(@Param("state")Integer state, @Param("type")Integer type, @Param("itemId")Integer itemId,@Param("userId")Integer userId);

    List<Record> selectlistByDec(@Param("state")Integer state, @Param("recordDec")String recordDec, @Param("itemList")List<Integer> itemList,@Param("userId")Integer userId);

    List<Record> selectByOfferId(@Param("state")Integer state, @Param("offerId")Integer offerId);

    List<RecordDecNum> selectAmountMaterial(@Param("itemId")Integer itemId, @Param("offerId")Integer offerId,@Param("recordType")Integer recordType,@Param("state")Integer state);

    BigDecimal selectAmountPrice(@Param("itemId")Integer itemId, @Param("offerId")Integer offerId,@Param("state")Integer state);

    BigDecimal selectMaterialAmountPrice(@Param("itemId")Integer itemId, @Param("offerId")Integer offerId,@Param("state")Integer state, @Param("recordType")Integer recordType);

    int deleteByItemId(Integer itemId);

}