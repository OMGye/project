package com.service;

import com.common.ServerResponse;
import com.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by upupgogogo on 2018/7/29.下午5:02
 */
public interface UserService {

    ServerResponse<User> login(User user);

    ServerResponse<String> addUser(User user, MultipartFile file, String path);

    ServerResponse<List<User>> ableUserList();

    ServerResponse<List<User>> unAbleUserList();

    ServerResponse<String> updateUser(User user);
}
