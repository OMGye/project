package com.dao;

import com.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserNameAndPassword(@Param("userName")String userName, @Param("password")String password);

    List<User> selectUserList();

    List<User> selectByUserName(String userName);

    int updateItemId(@Param("userIds") List<Integer> userIds, @Param("itemId") Integer itemId);

    List<User> selectByUserType(Integer userType);

    int updateSetItemBeNullByItemId(Integer itemId);

    int updateManagerByItemId(Integer itemId);

    int updateUploadByItemId(Integer itemId);
}