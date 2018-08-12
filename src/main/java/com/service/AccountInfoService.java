package com.service;

import com.common.ServerResponse;
import com.pojo.AccountInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by upupgogogo on 2018/8/12.下午12:49
 */
public interface AccountInfoService {

    ServerResponse insert(AccountInfo accountInfo, Integer materialInfoId, MultipartFile file, String path);
}
