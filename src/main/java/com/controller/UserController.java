package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
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

    @RequestMapping(value = "login.do",method = RequestMethod.GET)
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

    @RequestMapping(value = "adduser.do",method = RequestMethod.GET)
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
    public ServerResponse<List<User>> listAbleUser(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return userService.ableUserList();
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "listunableuser.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<User>> listUnAbleUser(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return userService.unAbleUserList();
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "updateuser.do",method = RequestMethod.GET)
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


}
