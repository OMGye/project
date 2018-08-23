package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.github.pagehelper.PageInfo;
import com.pojo.Item;
import com.pojo.User;
import com.service.ItemService;
import com.vo.UserAccountVo;
import com.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by upupgogogo on 2018/8/2.上午2:42
 */
@Controller
@RequestMapping("/item/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    private Logger logger = LoggerFactory.getLogger(ItemController.class);

    @RequestMapping(value = "additem.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addItem(HttpSession session, Integer userId,String itemName, String itemDec, Integer accountUserId, Integer accountCheckUserId, Integer materialUserId, Integer materialCheckUserId, String endTime){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
             Item item = new Item();
             item.setItemDec(itemDec);
             item.setUserId(userId);
             item.setItemName(itemName);
            if (item.getUserId() == null && endTime == null)
                return ServerResponse.createByErrorMessage("请选定负责人");
            try {
                return itemService.addNewItem(item,accountUserId,accountCheckUserId,materialUserId,materialCheckUserId,endTime);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return ServerResponse.createByErrorMessage("参数错误");
            }
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "getuserforcheck.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<UserVo>> getLeader(HttpSession session,Integer userType){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() ){
            if (userType > 7)
                return ServerResponse.createByErrorMessage("参数错误");
            return itemService.getUserForCheck(userType);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "listitem.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listitem(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return itemService.listItemVo(user,pageNum,pageSize);

    }

    @RequestMapping(value = "getaccountbyitemid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<UserAccountVo> getAccount(HttpSession session, Integer itemId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }

        return itemService.getAccountByItemId(itemId);
    }

    @RequestMapping(value = "updateitemalluser.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<UserAccountVo> updateItemAllUser(HttpSession session, Integer itemId, Integer manageId,Integer accountUserId, Integer accountCheckUserId, Integer materialUserId, Integer materialCheckUserId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }

        return itemService.updateItemAllUser(itemId, manageId,accountUserId, accountCheckUserId, materialUserId, materialCheckUserId);
    }




}
