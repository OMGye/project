package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.pojo.Category;
import com.pojo.Item;
import com.pojo.User;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by upupgogogo on 2018/8/6.下午12:46
 */

@Controller
@RequestMapping("/category/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "addcategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(Category category, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            if (category.getCategoryName() == null)
                return ServerResponse.createByErrorMessage("名字不能为空");
            return categoryService.add(category);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "deletecategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delete(Integer categoryId, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return categoryService.delete(categoryId);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "categorylist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }

        return categoryService.list(pageSize,pageNum);
    }

    @RequestMapping(value = "updatecategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(Category category, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return categoryService.update(category);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }
}
