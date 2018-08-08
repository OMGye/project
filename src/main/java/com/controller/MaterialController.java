package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by upupgogogo on 2018/8/6.下午4:00
 */

@Controller
@RequestMapping("/material/")
public class MaterialController {


    @Autowired
    private MaterialService materialService;

    @RequestMapping(value = "buymaterial.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse buyMaterial(MaterialBuyInfo materialBuyInfo, HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }
        if (materialBuyInfo.getItemId() == null)
            return ServerResponse.createByErrorMessage("没有传入项目");
        if (UserAuth.MATERIAL_UPLOAD.getCode() == user.getUserType() && user.getItemId() == materialBuyInfo.getItemId()){
            materialBuyInfo.setUserId(user.getUserId());
            String path = request.getSession().getServletContext().getRealPath("upload");
            return materialService.buyMaterial(materialBuyInfo,file,path);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "getmaterialstock.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<MaterialStock>> getMaterialStock(HttpSession session, Integer itemId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if (itemId == null)
            return ServerResponse.createByErrorMessage("没有传入项目");
        if (UserAuth.MATERIAL_UPLOAD.getCode() == user.getUserType() && user.getItemId() ==itemId){
            return materialService.getMaterialStockByItemId(itemId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "usematerial.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse useMaterial(HttpSession session, MaterialUseInfo materialUseInfo){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if (materialUseInfo.getItemId() == null)
            return ServerResponse.createByErrorMessage("没有传入项目");
        if (UserAuth.MATERIAL_UPLOAD.getCode() == user.getUserType() && user.getItemId() == materialUseInfo.getItemId()){
            materialUseInfo.setUserId(user.getUserId());
            return materialService.useMaterial(materialUseInfo);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "check.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse useMaterial(HttpSession session, Integer materialUserId, Integer materialInfoId, Integer itemId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if (itemId == null)
            return ServerResponse.createByErrorMessage("没有传入项目");
        if (UserAuth.MATERIAL_UPLOAD.getCode() == user.getUserType() && user.getItemId() == itemId){
           return materialService.updateState(materialUserId,materialInfoId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "uncheckbuylist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<MaterialBuyInfo>> getUnCheckList(HttpSession session,Integer itemId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return materialService.getUncheckList(itemId);
    }

    @RequestMapping(value = "uncheckuselist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<MaterialUseInfo>> getUnCheckUseList(HttpSession session,Integer itemId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return materialService.getUncheckUseList(itemId);
    }

    @RequestMapping(value = "materialdetail.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List> materialDetail(HttpSession session,Integer materialUserId, Integer materialInfoId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return materialService.getMaterialDetail(materialUserId,materialInfoId);
    }



}
