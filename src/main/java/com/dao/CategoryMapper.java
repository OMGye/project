package com.dao;

import com.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectList();

    List<Category> selectCountByName(String offerCompany);

    List<Category> selectCountByNameAndId(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId);

    List<Category> selectByCategoryName(String categoryName);
}