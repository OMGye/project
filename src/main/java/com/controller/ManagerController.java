package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.pojo.Record;
import com.pojo.User;
import com.service.RecordService;
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
 * Created by upupgogogo on 2018/9/28.下午4:00
 */
@Controller
@RequestMapping("/manager/")
public class ManagerController {

    @Autowired
    private RecordService recordService;

    @RequestMapping(value = "record/addrecord.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addRecor(HttpSession session, Record record, @RequestParam(value = "upload_file",required = false) MultipartFile[] files, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.MANAGER.getCode() == user.getUserType() && user.getItemId() != null){
            String path = request.getSession().getServletContext().getRealPath("upload");
            return recordService.addRecord(user,record,path,files);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }
}
