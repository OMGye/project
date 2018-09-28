package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.pojo.Record;
import com.pojo.User;
import com.service.ItemService;
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
 * Created by upupgogogo on 2018/9/4.下午1:46
 */
@Controller
@RequestMapping("/uploader/")
public class UploaderController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "record/addrecord.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addRecor(HttpSession session, Record record, @RequestParam(value = "upload_file",required = false) MultipartFile[] files, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.ITEM_UPLOAD.getCode() == user.getUserType() && user.getItemId() != null){
            String path = request.getSession().getServletContext().getRealPath("upload");
            return recordService.addRecord(user,record,path,files);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/addrecordimg.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addRecorImg(HttpSession session, Integer recordId, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.ITEM_UPLOAD.getCode() == user.getUserType() && user.getItemId() != null){
            String path = request.getSession().getServletContext().getRealPath("upload");
            return recordService.addRecordImg(recordId,path,file);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/deleterecordimg.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteRecorImg(HttpSession session, Integer recordId, String fileName, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.ITEM_UPLOAD.getCode() == user.getUserType() && user.getItemId() != null){
            String path = request.getSession().getServletContext().getRealPath("upload");
            return recordService.deleteRecordImg(recordId,fileName);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(HttpSession session, Integer state, Integer type){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.ITEM_UPLOAD.getCode() == user.getUserType() && user.getItemId() != null){
            return itemService.getItemById(user.getItemId());
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @RequestMapping(value = "item/itemdetail.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse itemDetail(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.ITEM_UPLOAD.getCode() == user.getUserType() && user.getItemId() != null){
            return itemService.getItemById(user.getItemId());
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }



}
