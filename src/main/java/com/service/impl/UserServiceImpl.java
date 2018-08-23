package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.dao.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pojo.Item;
import com.pojo.User;
import com.service.UserService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.util.DateTimeUtil;
import com.util.FTPUtil;
import com.util.PropertiesUtil;
import com.vo.UserAccountVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by upupgogogo on 2018/7/29.下午5:03
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private MaterialBuyInfoMapper materialBuyInfoMapper;

    @Autowired
    private MaterialUseInfoMapper materialUseInfoMapper;

    @Override
    public ServerResponse<User> login(User user) {
        User retUser = userMapper.selectByUserNameAndPassword(user.getUserName(),user.getPassword());
        if (retUser == null)
            return ServerResponse.createByErrorMessage("账号或密码错误");
        if (retUser.getState() == Const.User.ACTIVATE)
            return ServerResponse.createBySuccess(retUser);
        return ServerResponse.createByErrorMessage("账号未激活");
    }

    @Override
    public ServerResponse<String> addUser(User user, MultipartFile file, String path) {

        if (StringUtils.isBlank(user.getUserName()) | StringUtils.isBlank(user.getPassword()) | StringUtils.isBlank(user.getPhone() )| user.getUserType() == null )
            return ServerResponse.createByErrorMessage("参数错误");
        String fileName = file.getOriginalFilename();
        //扩展名
        //abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);


        try {
            file.transferTo(targetFile);
            //文件已经上传成功了


            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到ftp服务器上

            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }
        user.setState(new Byte((byte) Const.User.ACTIVATE));
        user.setUserImg(PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
        int res = userMapper.insert(user);
        if (res <= 0)
            return ServerResponse.createByErrorMessage("创建用户失败");
        return ServerResponse.createBySuccessMessage("创建用户成功");
    }

    @Override
    public ServerResponse<PageInfo> ableUserList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("user_id desc");
        List<User> list = userMapper.selectUserList();
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<PageInfo> unAbleUserList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("user_id desc");
        List<User> list = userMapper.selectUnAbleUserList();
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<String> updateUser(User user) {
        int res = userMapper.updateByPrimaryKeySelective(user);
        if (res > 0)
            return ServerResponse.createBySuccess("操作成功");
        return ServerResponse.createBySuccess("操作失败");
    }

    @Override
    public ServerResponse<User> getUserInfo(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null)
            return ServerResponse.createByErrorMessage("没有该用户");
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse<User> update(User user, MultipartFile file, String path) {
        if (file != null){
            String fileName = file.getOriginalFilename();
            //扩展名
            //abc.jpg
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
            String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
            logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

            File fileDir = new File(path);
            if(!fileDir.exists()){
                fileDir.setWritable(true);
                fileDir.mkdirs();
            }
            File targetFile = new File(path,uploadFileName);


            try {
                file.transferTo(targetFile);
                //文件已经上传成功了


                FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                //已经上传到ftp服务器上

                targetFile.delete();
            } catch (IOException e) {
                logger.error("上传文件异常",e);
                return null;
            }
            user.setUserImg(PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
        }
        int row = userMapper.updateByPrimaryKeySelective(user);
        User selectUser = userMapper.selectByPrimaryKey(user.getUserId());
        return ServerResponse.createBySuccess(selectUser);
    }

    @Override
    public ServerResponse<List<String>> unDeal(User user) {
        List<String> list = new ArrayList<>();
        if (user.getUserType() == UserAuth.BOSS.getCode()){
            Integer itemCount = itemMapper.count();
            Integer userCount = userMapper.selectCount();
            if (itemCount * 5 != userCount)
                list.add("存在项目相应负责人没有创建");

        }
        if (user.getUserType() == UserAuth.MANAGER.getCode()){
            if (user.getItemId() == null){
                int count = accountInfoMapper.selectCountByUserIdCheck(user.getUserId());
                if (count > 0)
                    list.add("您有财务记录需要确认");

            }
            else {
                int count = userMapper.selectCountByItem(user.getItemId());
                if (count <= 5)
                    list.add("存在项目相应负责人没有创建");
                int accountCount = accountInfoMapper.selectCountByUserIdCheck(user.getUserId());
                if (accountCount > 0)
                    list.add("您有财务记录需要确认");

            }
        }
        if (user.getUserType() == UserAuth.MATERIAL_UPLOAD.getCode() || user.getUserType() == UserAuth.EMPLOYEE.getCode() || user.getUserType() == UserAuth.ACCOUNT_UPLOAD.getCode()){
            int accountCount = accountInfoMapper.selectCountByUserIdCheck(user.getUserId());
            if (accountCount > 0)
                list.add("您有财务记录需要确认");

        }
        if (user.getUserType() == UserAuth.FINANCIAL.getCode() || user.getUserType() == UserAuth.ACCOUNT_CHECKED.getCode()){
            int count = accountInfoMapper.selectCountByUserIdUncheck(user.getUserName());
            if (count > 0)
                list.add("您有财务记录需要审核");

        }
        if (user.getUserType() == UserAuth.MATERIAL_CHECKED.getCode()) {
            if (user.getItemId() != null) {
                int countOne = materialBuyInfoMapper.count(user.getItemId());
                int countTwo = materialUseInfoMapper.count(user.getItemId());
                if (countOne + countTwo > 0)
                    list.add("您有材料记录需要审核");
            }
            int accountCount = accountInfoMapper.selectCountByUserIdCheck(user.getUserId());
            if (accountCount > 0)
                list.add("您有财务记录需要确认");
        }
        if (list.size() == 0)
            list.add("事件都以处理完毕");
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<UserAccountVo> getAccountByUserId(User user) {
        if (user.getUserType() == UserAuth.BOSS.getCode()){
            UserAccountVo userAccountVo = new UserAccountVo();
             userAccountVo.setDayPayAccount(accountInfoMapper.selectAllPayAccountDay());
             userAccountVo.setDayIncomeAccount(accountInfoMapper.selectAllIncomeAccountDay());
             userAccountVo.setMonthPayAccount(accountInfoMapper.selectAllPayAccountMonth());
             userAccountVo.setMonthIncomeAccount(accountInfoMapper.selectAllIncomeAccountMonth());
            if (userAccountVo.getDayIncomeAccount() == null){
                userAccountVo.setDayIncomeAccount(new BigDecimal("0"));
            }
            if (userAccountVo.getDayPayAccount() == null){
                userAccountVo.setDayPayAccount(new BigDecimal("0"));
            }
            if (userAccountVo.getMonthIncomeAccount() == null){
                userAccountVo.setMonthIncomeAccount(new BigDecimal("0"));
            }
            if (userAccountVo.getMonthPayAccount() == null){
                userAccountVo.setMonthPayAccount(new BigDecimal("0"));
            }
             return ServerResponse.createBySuccess(userAccountVo);
        }

        if (user.getUserType() == UserAuth.MANAGER.getCode()){
            if (user.getItemId() != null) {
                UserAccountVo userAccountVo = new UserAccountVo();
                userAccountVo.setDayPayAccount(accountInfoMapper.selectAllPayAccountDayByItemId(user.getItemId()));
                userAccountVo.setDayIncomeAccount(accountInfoMapper.selectAllIncomeAccountDayByItemId(user.getItemId()));
                userAccountVo.setMonthPayAccount(accountInfoMapper.selectAllPayAccountMonthByItemId(user.getItemId()));
                userAccountVo.setMonthIncomeAccount(accountInfoMapper.selectAllIncomeAccountMonthByItemId(user.getItemId()));
                if (userAccountVo.getDayIncomeAccount() == null){
                    userAccountVo.setDayIncomeAccount(new BigDecimal("0"));
                }
                if (userAccountVo.getDayPayAccount() == null){
                    userAccountVo.setDayPayAccount(new BigDecimal("0"));
                }
                if (userAccountVo.getMonthIncomeAccount() == null){
                    userAccountVo.setMonthIncomeAccount(new BigDecimal("0"));
                }
                if (userAccountVo.getMonthPayAccount() == null){
                    userAccountVo.setMonthPayAccount(new BigDecimal("0"));
                }
                return ServerResponse.createBySuccess(userAccountVo);
            }
        }

        UserAccountVo userAccountVo = new UserAccountVo();
        userAccountVo.setDayPayAccount(accountInfoMapper.selectAllPayAccountDayByUserId(user.getUserId()));
        userAccountVo.setDayIncomeAccount(accountInfoMapper.selectAllIncomeAccountDayByUserId(user.getUserId()));
        userAccountVo.setMonthPayAccount(accountInfoMapper.selectAllPayAccountMonthByUserId(user.getUserId()));
        userAccountVo.setMonthIncomeAccount(accountInfoMapper.selectAllIncomeAccountMonthByUserId(user.getUserId()));
        if (userAccountVo.getDayIncomeAccount() == null){
            userAccountVo.setDayIncomeAccount(new BigDecimal("0"));
        }
        if (userAccountVo.getDayPayAccount() == null){
            userAccountVo.setDayPayAccount(new BigDecimal("0"));
        }
        if (userAccountVo.getMonthIncomeAccount() == null){
            userAccountVo.setMonthIncomeAccount(new BigDecimal("0"));
        }
        if (userAccountVo.getMonthPayAccount() == null){
            userAccountVo.setMonthPayAccount(new BigDecimal("0"));
        }
        return ServerResponse.createBySuccess(userAccountVo);


    }
}
