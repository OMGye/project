package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.dao.ItemMapper;
import com.dao.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Item;
import com.service.ItemService;
import com.vo.ItemListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by upupgogogo on 2018/8/2.上午2:43
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Transactional
    public ServerResponse addNewItem(Item item, Integer accountUserId, Integer accountCheckUserId, Integer materialUserId, Integer materialCheckUserId, String endTime) throws Exception{
        item.setState(Const.Item.WORKING);
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=sDateFormat.parse(endTime);
        item.setEndTime(date);

        Integer itemId = itemMapper.insert(item);
        if (itemId == null)
            return ServerResponse.createByErrorMessage("新建项目失败");
        List<Integer> userIds = new ArrayList<>();
        userIds.add(accountCheckUserId);
        userIds.add(accountUserId);
        userIds.add(materialCheckUserId);
        userIds.add(materialUserId);
        userIds.add(item.getUserId());

        int row = userMapper.updateItemId(userIds,itemId);
        if (row < 5){
            itemMapper.deleteByPrimaryKey(itemId);
            throw new Exception();
        }

        return ServerResponse.createBySuccess("新建项目成功");
    }

    @Override
    public ServerResponse<PageInfo> listItemVo(Integer userId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<Item> list = new ArrayList<>();
        if (UserAuth.BOSS.getCode() == userId) {
            list = itemMapper.select();
        }
        else{
            list = itemMapper.selectByUserId(userId);

        }
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(assembleItemListVo(list));
        return ServerResponse.createBySuccess(pageInfo);
    }

    private List<ItemListVo> assembleItemListVo(List<Item> list){
        List<ItemListVo> listVos = new ArrayList<>();
        for (Item item : list){
            ItemListVo listVo = new ItemListVo();
            listVo.setItemId(item.getItemId());
            listVo.setItemName(item.getItemName());
            listVo.setCreateTime(item.getCreateTime());
            listVo.setEndTime(item.getEndTime());
            listVos.add(listVo);
        }
        return listVos;
    }
}
