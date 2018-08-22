package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.github.pagehelper.PageInfo;
import com.pojo.User;
import com.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
 * Created by upupgogogo on 2018/7/29.下午4:21
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(User user, HttpSession session){
        if (StringUtils.isBlank(user.getPassword()) | StringUtils.isBlank(user.getUserName()))
            return ServerResponse.createByErrorMessage("请输入用户名或密码");
        ServerResponse<User> response = userService.login(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "adduser.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addUser(User addUser, HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            return userService.addUser(addUser,file,path);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");

    }

    @RequestMapping(value = "listableuser.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listAbleUser(HttpSession session,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return userService.ableUserList(pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "listunableuser.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listUnAbleUser(HttpSession session,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5  ") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return userService.unAbleUserList(pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "updateuser.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> updateUser(HttpSession session,User reUser){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return userService.updateUser(reUser);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "getuserinfo.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getUserInfo(HttpSession session, Integer userId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
            if (userId == null)
                return ServerResponse.createByErrorMessage("参数错误");
            return userService.getUserInfo(userId);

    }

    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess("注销成功");
    }

    @RequestMapping(value = "updatemyself.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(HttpSession session,HttpServletRequest request, User updateUser,@RequestParam(value = "upload_file",required = false) MultipartFile file){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        updateUser.setUserId(user.getUserId());
        updateUser.setState(user.getState());
        updateUser.setUserType(user.getUserType());
        return userService.update(updateUser,file,request.getSession().getServletContext().getRealPath("upload"));
    }

    @RequestMapping(value = "undeal.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<String>> unDeal(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return userService.unDeal(user);
    }

    @RequestMapping(value = "getaccountbyuserid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getAccountByUserId(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return userService.getAccountByUserId(user);
    }


}



