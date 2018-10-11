package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.Item;
import com.pojo.User;
import com.vo.ItemVo;
import com.vo.UserAccountVo;
import com.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by upupgogogo on 2018/8/2.上午2:43
 */
public interface ItemService {

    ServerResponse addNewItem(Item item, MultipartFile file, String path, String endTime);

    ServerResponse<PageInfo<Item>> listItem(int pageNum, int pageSize);

    ServerResponse<List<UserVo>> getUserForCheck(Integer userType);


    ServerResponse updateItem(Item item, MultipartFile file, String path, String endTime);

    ServerResponse<PageInfo> getItemByName(int pageNum, int pageSize, String itemName);

    ServerResponse<Item> getItemById(Integer itemId);
}
