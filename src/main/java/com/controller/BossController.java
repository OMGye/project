package com.controller;

import com.common.Const;
import com.common.ResponseCode;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.github.pagehelper.PageInfo;
import com.pojo.Category;
import com.pojo.Item;
import com.pojo.OfferMaterial;
import com.pojo.User;
import com.service.CategoryService;
import com.service.ItemService;
import com.service.OfferMaterialService;
import com.service.UserService;
import com.vo.ItemVo;
import com.vo.UserAccountVo;
import com.vo.UserPersonInfoVo;
import com.vo.UserVo;
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
    public ServerResponse<List<User>> getUserName(HttpSession session, String userName){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return userService.getUserByUserName(userName);
    }


    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "item/additem.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addItem(HttpSession session, Item item, String endTime,HttpServletRequest request,@RequestParam(value = "upload_file",required = false) MultipartFile file){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return itemService.addNewItem(item,file,request.getSession().getServletContext().getRealPath("upload"),endTime);
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
    public ServerResponse<List<Item>> getItembyName(String itemName, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if (UserAuth.BOSS.getCode() == user.getUserType()){
            return itemService.getItemByName(itemName);
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
    public ServerResponse<List<Category>> getCategoryByName(String categoryName, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return categoryService.getCategoryByName(categoryName);
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
    public ServerResponse<List<OfferMaterial>> getOffererByName(String offerCompany, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        return offerMaterialService.getOffererByName(offerCompany);
    }


}
