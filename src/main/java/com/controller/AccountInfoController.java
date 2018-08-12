package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.pojo.AccountInfo;
import com.pojo.User;
import com.service.AccountInfoService;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by upupgogogo on 2018/8/11.下午10:31
 */
@Controller
@RequestMapping("/account/")
public class AccountInfoController {

    @Autowired
    private AccountInfoService accountInfoService;


    @RequestMapping(value = "addaccount.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addAccount(HttpServletRequest request, HttpSession session, AccountInfo accountInfo, Integer materialInfoId, @RequestParam(value = "upload_file",required = false) MultipartFile file){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }
        accountInfo.setUserId(user.getUserId());
        String path = request.getSession().getServletContext().getRealPath("upload");
        return accountInfoService.insert(accountInfo,materialInfoId,file,path);
    }
}