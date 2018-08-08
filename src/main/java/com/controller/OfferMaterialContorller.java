package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.pojo.OfferMaterial;
import com.pojo.User;
import com.service.OfferMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by upupgogogo on 2018/8/6.下午2:20
 */
@Controller
@RequestMapping("/offerer/")
public class OfferMaterialContorller {

    @Autowired
    private OfferMaterialService offerMaterialService;

    @RequestMapping(value = "addofferer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(OfferMaterial offerMaterial, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return offerMaterialService.add(offerMaterial);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "deleteofferer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delete(Integer offerId, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return offerMaterialService.delete(offerId);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "offererlist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }

        return offerMaterialService.list(pageSize,pageNum);

    }

    @RequestMapping(value = "updateofferer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(OfferMaterial offerMaterial, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return offerMaterialService.update(offerMaterial);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }
}
