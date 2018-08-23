package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.AccountInfo;
import com.vo.UserAccountVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by upupgogogo on 2018/8/12.下午12:49
 */
public interface AccountInfoService {

    ServerResponse insert(AccountInfo accountInfo, Integer materialInfoId, MultipartFile file, String path);

    ServerResponse<PageInfo<AccountInfo>> checkUserList(int pageSize, int pageNum, Integer itemId);

    ServerResponse checkUserConfirm(Integer accountInfoId);

    ServerResponse<PageInfo<AccountInfo>> userList(int pageSize, int pageNum,Integer userId, Integer itemId);

    ServerResponse userConfirm(Integer accountInfoId);

    ServerResponse<PageInfo<AccountInfo>> checkUserList(int pageSize, int pageNum);

    ServerResponse<PageInfo<AccountInfo>> userList(int pageSize, int pageNum,Integer userId);

    ServerResponse<AccountInfo> getAccountById(Integer accountInfoId);

    ServerResponse<PageInfo<AccountInfo>> getItemAccountList(int pageSize, int pageNum, Integer itemId);

    ServerResponse<PageInfo<AccountInfo>> getAccountList(int pageSize, int pageNum);

    ServerResponse<UserAccountVo> getAccount();

    ServerResponse<List<AccountInfo>> getAccountListByTime(String startTime ,String endTime);
}
