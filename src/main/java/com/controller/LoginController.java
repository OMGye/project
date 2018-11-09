package com.controller;

import com.common.Const;
import com.common.ServerResponse;
import com.pojo.User;
import com.service.UserService;
import com.util.UserListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by upupgogogo on 2018/9/3.下午3:27
 */
@Controller
@RequestMapping("/login/")
public class LoginController {

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
}
