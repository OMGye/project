package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.Item;
import com.pojo.User;
import com.vo.ItemVo;
import com.vo.UserAccountVo;
import com.vo.UserVo;

import java.util.List;

/**
 * Created by upupgogogo on 2018/8/2.上午2:43
 */
public interface ItemService {

    ServerResponse addNewItem(Item item, Integer accountUserId, Integer accountCheckUserId, Integer materialUserId, Integer materialCheckUserId, String endTime)throws Exception;

    ServerResponse<PageInfo<ItemVo>> listItemVo(User user, int pageNum, int pageSize);

    ServerResponse<List<UserVo>> getUserForCheck(Integer userType);

    ServerResponse<UserAccountVo> getAccountByItemId(Integer itemId);

    ServerResponse updateItemAllUser(Integer itemId,Integer manageId,Integer accountUserId, Integer accountCheckUserId, Integer materialUserId, Integer materialCheckUserId);


}
