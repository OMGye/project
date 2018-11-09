package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.User;
import com.vo.UserAccountVo;
import com.vo.UserPersonInfoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by upupgogogo on 2018/7/29.下午5:02
 */
public interface UserService {

    ServerResponse<User> login(User user);

    ServerResponse<String> addUser(User user, MultipartFile file, String path);

    ServerResponse<PageInfo> ableUserList(int pageNum, int pageSize);

    ServerResponse<String> updateUser(User user);

    ServerResponse<UserPersonInfoVo> getUserInfo(Integer userId);

    ServerResponse<User> update(User user, MultipartFile file, String path);

    ServerResponse<PageInfo<User>> getUserByUserName(int pageNum, int pageSize,String userName);

}
