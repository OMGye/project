package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.dao.AccountInfoMapper;
import com.dao.ItemMapper;
import com.dao.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Item;
import com.pojo.User;
import com.service.ItemService;
import com.vo.ItemListVo;
import com.vo.ItemVo;
import com.vo.UserAccountVo;
import com.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Transactional
    public ServerResponse addNewItem(Item item, Integer accountUserId, Integer accountCheckUserId, Integer materialUserId, Integer materialCheckUserId, String endTime) throws Exception{
        Item selectItem = itemMapper.selectByItemName(item.getItemName());
        if (selectItem != null){
            return ServerResponse.createByErrorMessage("项目名已经存在");
        }
        item.setState(Const.Item.WORKING);
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=sDateFormat.parse(endTime);
        item.setEndTime(date);

        Integer itemId = itemMapper.insert(item);
        if (itemId < 0)
            return ServerResponse.createByErrorMessage("新建项目失败");
        List<Integer> userIds = new ArrayList<>();
        if (accountCheckUserId != null)
            userIds.add(accountCheckUserId);
        if (accountUserId != null)
            userIds.add(accountUserId);
        if (materialCheckUserId != null)
            userIds.add(materialCheckUserId);
        if (materialUserId != null)
            userIds.add(materialUserId);
        userIds.add(item.getUserId());

        int row = userMapper.updateItemId(userIds,item.getItemId());
        if (row < userIds.size()){
            itemMapper.deleteByPrimaryKey(item.getItemId());
            throw new Exception();
        }

        return ServerResponse.createBySuccess("新建项目成功");
    }

    @Override
    public ServerResponse<PageInfo<ItemVo>> listItemVo(User user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<Item> list = itemMapper.select();
        PageInfo<ItemVo> pageInfo = new PageInfo(list);
        pageInfo.setList(assembleItemListVo(list));
        return ServerResponse.createBySuccess(pageInfo);
    }

    private List<ItemVo> assembleItemListVo(List<Item> list){
        List<ItemVo> listVos = new ArrayList<>();
        for (Item item : list){
            ItemVo itemVo = new ItemVo();
            BeanUtils.copyProperties(item,itemVo);
            List<User> userList = userMapper.selectByItemId(item.getItemId());
            if (userList.size() > 1){
                for (User user : userList){
                    if (user.getUserType() == UserAuth.ACCOUNT_CHECKED.getCode()){
                        itemVo.setAccountCheckUser(user.getUserName());
                        itemVo.setAccountCheckUserId(user.getUserId());
                    }
                    if (user.getUserType() == UserAuth.ACCOUNT_UPLOAD.getCode()){
                        itemVo.setAccountUser(user.getUserName());
                        itemVo.setAccountUserId(user.getUserId());
                    }
                    if (user.getUserType() == UserAuth.MATERIAL_CHECKED.getCode()){
                        itemVo.setMaterialCheckUser(user.getUserName());
                        itemVo.setMaterialCheckUserId(user.getUserId());
                    }
                    if (user.getUserType() == UserAuth.MATERIAL_UPLOAD.getCode()){
                        itemVo.setMaterialUser(user.getUserName());
                        itemVo.setMaterialUserId(user.getUserId());
                    }
                }
            }
            listVos.add(itemVo);
        }
        return listVos;
    }

    @Override
    public ServerResponse<List<UserVo>> getUserForCheck(Integer userType) {

        List<User> list = userMapper.selectByUserType(userType);
        List<UserVo> userVoList = new ArrayList<>();
        for (User user : list){
            UserVo userVo = new UserVo(user.getUserId(),user.getUserName());
            userVoList.add(userVo);
        }
        return ServerResponse.createBySuccess(userVoList);
    }

    @Override
    public ServerResponse<UserAccountVo> getAccountByItemId(Integer itemId) {
        if (itemId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        UserAccountVo userAccountVo = new UserAccountVo();
        userAccountVo.setDayPayAccount(accountInfoMapper.selectAllPayAccountDayByItemId(itemId));
        userAccountVo.setDayIncomeAccount(accountInfoMapper.selectAllIncomeAccountDayByItemId(itemId));
        userAccountVo.setMonthPayAccount(accountInfoMapper.selectAllPayAccountMonthByItemId(itemId));
        userAccountVo.setMonthIncomeAccount(accountInfoMapper.selectAllIncomeAccountMonthByItemId(itemId));
        if (userAccountVo.getDayIncomeAccount() == null){
            userAccountVo.setDayIncomeAccount(new BigDecimal("0"));
        }
        if (userAccountVo.getDayPayAccount() == null){
            userAccountVo.setDayPayAccount(new BigDecimal("0"));
        }
        if (userAccountVo.getMonthIncomeAccount() == null){
            userAccountVo.setMonthIncomeAccount(new BigDecimal("0"));
        }
        if (userAccountVo.getMonthPayAccount() == null){
            userAccountVo.setMonthPayAccount(new BigDecimal("0"));
        }
        return ServerResponse.createBySuccess(userAccountVo);
    }

    @Override
    public ServerResponse updateItemAllUser(Integer itemId, Integer manageId,Integer accountUserId, Integer accountCheckUserId, Integer materialUserId, Integer materialCheckUserId) {
        if (itemId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        Item item = new Item();
        item.setItemId(itemId);
        List<Integer> userIds = new ArrayList<>();
        if (manageId != null){
            item.setUserId(manageId);
            userIds.add(item.getUserId());
            itemMapper.updateByPrimaryKeySelective(item);
        }

        if (accountCheckUserId != null)
            userIds.add(accountCheckUserId);
        if (accountUserId != null)
            userIds.add(accountUserId);
        if (materialCheckUserId != null)
            userIds.add(materialCheckUserId);
        if (materialUserId != null)
            userIds.add(materialUserId);
        if (userIds.size() > 0){
            int count = userMapper.updateItemId(userIds,item.getItemId());
            if (count < userIds.size())
                return ServerResponse.createByErrorMessage("修改失败");
        }
        return ServerResponse.createBySuccess("修改成功");
    }
}
