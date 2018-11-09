package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.github.pagehelper.PageInfo;
import com.pojo.Item;
import com.pojo.User;
import com.service.ItemService;
import com.service.RecordService;
import com.service.UserService;
import com.vo.IndexVo;
import com.vo.RecordAmountVo;
import com.vo.UserPersonInfoVo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by upupgogogo on 2018/9/30.下午1:53
 */
@Controller
@RequestMapping("/financial/")
public class FinancialController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "record/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpSession session, Integer state, Integer type , Integer itemId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.FINANCIAL.getCode() == user.getUserType()){
            return recordService.AllList(itemId,state,type,pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/check.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> check(HttpSession session, Integer recordId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.FINANCIAL.getCode() == user.getUserType()){
            return recordService.financialCheck(recordId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "item/listitem.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo<Item>> listitem(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.FINANCIAL.getCode() == user.getUserType()){
            return itemService.listItem(pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "item/getitembyname.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo<Item>> getItembyName(String itemName, HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.FINANCIAL.getCode() == user.getUserType()){
            return itemService.getItemByName(pageNum,pageSize,itemName);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "item/getitembyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Item> getItemById(HttpSession session, Integer itemId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.FINANCIAL.getCode() == user.getUserType() ){
            return itemService.getItemById(itemId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "user/updatemyself.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(HttpSession session, HttpServletRequest request, User updateUser, @RequestParam(value = "upload_file",required = false) MultipartFile file){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        updateUser.setUserId(user.getUserId());
        updateUser.setState(user.getState());
        updateUser.setUserType(user.getUserType());
        return userService.update(updateUser,file,request.getSession().getServletContext().getRealPath("upload"));
    }

    @RequestMapping(value = "user/getuserinfo.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<UserPersonInfoVo> getUserInfo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return userService.getUserInfo(user.getUserId());
    }

    @RequestMapping(value = "record/getrecordbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> getRecordById( HttpSession session,Integer recordId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.FINANCIAL.getCode() == user.getUserType()){
            return recordService.getByRecordId(user,recordId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/refuserecord.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> recordRefuse( HttpSession session,Integer recordId,String recordRefuse){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.FINANCIAL.getCode() == user.getUserType()){
            return recordService.refuseRecord(user,recordId,recordRefuse);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/listbydec.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listByRecordDec(HttpSession session, Integer state, String recordDec, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.FINANCIAL.getCode() == user.getUserType()){
            return recordService.listByDec(user,state,recordDec,pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "index/getindexvo.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<IndexVo> getIndexVo(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.FINANCIAL.getCode() == user.getUserType()){
            return recordService.getIndexVo();
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/getrecordamount.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<RecordAmountVo> getRecordAmount(HttpSession session, Integer itemId, Integer offerId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.FINANCIAL.getCode() == user.getUserType()){
            return recordService.getRecordAmount(itemId,offerId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/export.do", method = RequestMethod.GET)
    public void export(HttpSession session, HttpServletResponse response, Integer itemId, Integer type, Integer offerId) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=record.xlsx;charset=UTF-8");
        XSSFWorkbook workbook = recordService.exportExcelInfo(itemId,type,offerId);
        try {
            OutputStream output  = response.getOutputStream();
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
