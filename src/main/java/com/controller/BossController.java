package com.controller;
import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.service.*;
import com.vo.*;
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
 * Created by upupgogogo on 2018/9/3.下午4:07
 */
@Controller
@RequestMapping("/boss/")
public class BossController {
    @Autowired
    private UserService userService;


    @RequestMapping(value = "user/adduser.do",method = RequestMethod.POST)
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

    @RequestMapping(value = "user/listableuser.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listAbleUser(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return userService.ableUserList(pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "user/updateuser.do",method = RequestMethod.POST)
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

    @RequestMapping(value = "user/getuserinfo.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<UserPersonInfoVo> getUserInfo(HttpSession session, Integer userId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (userId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        return userService.getUserInfo(userId);

    }

    @RequestMapping(value = "user/logout.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.invalidate();
        return ServerResponse.createBySuccess("注销成功");
    }

    @RequestMapping(value = "user/updatemyself.do",method = RequestMethod.POST)
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

    @RequestMapping(value = "user/getuserbyname.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo<User>> getUserName(HttpSession session, String userName, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return userService.getUserByUserName(pageNum,pageSize,userName);
    }


    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "item/additem.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addItem(HttpSession session, Item item, String endTimeString,HttpServletRequest request,@RequestParam(value = "upload_file",required = false) MultipartFile[] files){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return itemService.addNewItem(item,files,request.getSession().getServletContext().getRealPath("upload"),endTimeString);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "item/addfile.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addItemFile(HttpSession session, Integer itemId, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            return itemService.addItemFile(itemId,path,file);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "item/deleteitem.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteItem(HttpSession session, Integer itemId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){

            return itemService.delete(itemId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "item/deletefile.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteItemFile(HttpSession session, Integer itemId, String fileName, String name, HttpServletRequest request){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            return itemService.deleteItemFile(itemId,fileName,name);
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
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return itemService.listItem(pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "item/updateitem.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<UserAccountVo> updateItemAllUser(HttpSession session, Item item, String endTime,HttpServletRequest request,@RequestParam(value = "upload_file",required = false) MultipartFile file){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }

        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return itemService.updateItem(item,file,request.getSession().getServletContext().getRealPath("upload"),endTime);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "item/getitembyname.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo<Item>> getItembyName(String itemName, HttpSession session,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return itemService.getItemByName(pageNum,pageSize,itemName);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "item/getuserforcheck.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<UserVo>> getLeader(HttpSession session, Integer userType){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() ){
            if (userType > 3)
                return ServerResponse.createByErrorMessage("参数错误");
            return itemService.getUserForCheck(userType);
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
        if (UserAuth.BOSS.getCode() == user.getUserType() ){
            return itemService.getItemById(itemId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "category/addcategory.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(Category category, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return categoryService.add(category);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "category/deletecategory.do",method = RequestMethod.POST)
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

    @RequestMapping(value = "category/categorylist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }

        return categoryService.list(pageSize,pageNum);
    }

    @RequestMapping(value = "category/updatecategory.do",method = RequestMethod.POST)
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
    @RequestMapping(value = "category/getcategorybyname.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo<Category>> getCategoryByName(String categoryName, HttpSession session,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return categoryService.getCategoryByName(pageSize,pageNum,categoryName);
    }

    @Autowired
    private OfferMaterialService offerMaterialService;

    @RequestMapping(value = "offer/addofferer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(OfferMaterial offerMaterial, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return offerMaterialService.add(offerMaterial);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "offer/deleteofferer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse offerdelete(Integer offerId, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return offerMaterialService.delete(offerId);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "offer/offererlist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse offerlist(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }

        return offerMaterialService.list(pageSize,pageNum);

    }

    @RequestMapping(value = "offer/updateofferer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(OfferMaterial offerMaterial, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return offerMaterialService.update(offerMaterial);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "offer/getoffererbyname.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo<OfferMaterial>> getOffererByName(String offerCompany, HttpSession session,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return offerMaterialService.getOffererByName(pageSize,pageNum,offerCompany);
    }

    @RequestMapping(value = "offer/getofferbyid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getOfferById(Integer offerId, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return offerMaterialService.getOffererById(offerId);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @Autowired
    private TransportService transportService;

    @RequestMapping(value = "transport/addtransport.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(Transport transport, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return transportService.add(transport);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "transport/deletetransport.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse transportdelete(Integer transportId, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return transportService.delete(transportId);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "transport/transportlist.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse transportlist(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }

        return transportService.list(pageSize,pageNum);

    }

    @RequestMapping(value = "transport/updatetransport.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(Transport transport, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return transportService.update(transport);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "transport/gettransportbyname.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo<Transport>> getTransportByName(String transportName, HttpSession session,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return transportService.getTransportByName(pageSize,pageNum,transportName);
    }

    @RequestMapping(value = "transport/gettransportbyid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getTransportById(Integer transportId, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType() || UserAuth.MANAGER.getCode() == user.getUserType()){
            return transportService.getTransportById(transportId);
        }

        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }


    @Autowired
    private RecordService recordService;

    @RequestMapping(value = "record/list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> list( HttpSession session,Integer state, Integer type , Integer itemId,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return recordService.AllList(itemId,state,type,pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/getrecordbyid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> getRecordById( HttpSession session,Integer recordId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return recordService.getByRecordId(user,recordId);
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
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return recordService.listByDec(user,state,recordDec,pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/listbyofferid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> getListByOfferId(HttpSession session, Integer state, Integer offerId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5")int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return recordService.listByOfferId(user,state,offerId,pageSize,pageNum);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/deletebyrecordid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo> deleteRecordById( HttpSession session,Integer recordId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return recordService.deleteByRecordId(user,recordId);
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
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return recordService.getRecordAmount(itemId,offerId);
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }

    @RequestMapping(value = "record/export.do", method = RequestMethod.GET)
    public void export(HttpSession session,HttpServletResponse response, Integer itemId, Integer type, Integer offerId) {
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

    @RequestMapping(value = "index/getindexvo.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<IndexVo> getIndexVo( HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return recordService.getIndexVo();
        }
        return ServerResponse.createByErrorMessage("请登入管理员账户");
    }





}
