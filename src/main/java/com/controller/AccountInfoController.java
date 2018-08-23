package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.AccountInfo;
import com.pojo.User;
import com.service.AccountInfoService;
import com.util.DBConnection;
import com.util.PageBean;
import com.vo.AccountItemVo;
import com.vo.UserAccountVo;
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

    @RequestMapping(value = "unchecklist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse unCheckList(HttpSession session, Integer itemId,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }
        if (itemId == null)
            return accountInfoService.checkUserList(pageSize,pageNum);
        return accountInfoService.checkUserList(pageSize,pageNum,itemId);
    }

    @RequestMapping(value = "checkconfirm.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse checkConfirm(HttpSession session, Integer accountInfoId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }
        return accountInfoService.checkUserConfirm(accountInfoId);
    }

    @RequestMapping(value = "userlist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse userList(HttpSession session, Integer itemId,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }
        if (itemId == null)
            return accountInfoService.userList(pageSize,pageNum,user.getUserId());
        return accountInfoService.userList(pageSize,pageNum,user.getUserId(),itemId);
    }

    @RequestMapping(value = "userconfirm.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse userConfirm(HttpSession session,Integer accountInfoId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }
        return accountInfoService.userConfirm(accountInfoId);
    }

    @RequestMapping(value = "getaccountbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getAccountById(HttpSession session,Integer accountInfoId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }
        return accountInfoService.getAccountById(accountInfoId);
    }

    @RequestMapping(value = "getaccountitem.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageBean<AccountItemVo>> getAccountItem(HttpSession session,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "2")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }
        PageBean<AccountItemVo> pageBean = DBConnection.getItemAccount(pageNum,pageSize);
        return ServerResponse.createBySuccess(pageBean);
    }

    @RequestMapping(value = "getaccountlist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo<AccountInfo>> getAccountList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "2")int pageSize, Integer itemId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }
        if (itemId == null)
            return accountInfoService.getAccountList(pageSize,pageNum);
        else
            return accountInfoService.getItemAccountList(pageSize,pageNum,itemId);

    }
    @RequestMapping(value = "getaccount.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<UserAccountVo> getAccount(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }

        return accountInfoService.getAccount();
    }

    @RequestMapping(value = "getaccountlistbytime.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<AccountInfo>> getAccountListByTime(HttpSession session,String startTime, String endTime){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登入");
        }

        return accountInfoService.getAccountListByTime(startTime, endTime);
    }


}
