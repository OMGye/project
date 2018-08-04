package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.Item;
import com.vo.UserVo;

import java.util.List;

/**
 * Created by upupgogogo on 2018/8/2.上午2:43
 */
public interface ItemService {

    ServerResponse addNewItem(Item item, Integer accountUserId, Integer accountCheckUserId, Integer materialUserId, Integer materialCheckUserId, String endTime)throws Exception;

    ServerResponse<PageInfo> listItemVo(Integer userId, int pageNum, int pageSize);

    ServerResponse<List<UserVo>> getUserForCheck(Integer userType);

}
