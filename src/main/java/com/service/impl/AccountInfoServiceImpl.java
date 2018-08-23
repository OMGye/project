package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.dao.AccountInfoMapper;
import com.dao.MaterialBuyInfoMapper;
import com.dao.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pojo.AccountInfo;
import com.pojo.MaterialBuyInfo;
import com.pojo.User;
import com.service.AccountInfoService;
import com.util.FTPUtil;
import com.util.PropertiesUtil;
import com.vo.UserAccountVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by upupgogogo on 2018/8/12.下午12:57
 */
@Service("accountInfoService")
public class AccountInfoServiceImpl implements AccountInfoService{

    private Logger logger = LoggerFactory.getLogger(AccountInfoServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Autowired
    private MaterialBuyInfoMapper materialBuyInfoMapper;

    @Override
    public ServerResponse insert(AccountInfo accountInfo, Integer materialInfoId, MultipartFile file, String path) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            //扩展名
            //abc.jpg
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
            String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
            logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.setWritable(true);
                fileDir.mkdirs();
            }
            File targetFile = new File(path, uploadFileName);


            try {
                file.transferTo(targetFile);
                //文件已经上传成功


                FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                //已经上传到ftp服务器上

                targetFile.delete();
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                return ServerResponse.createByErrorMessage("上传文件异常");
            }

            if (materialInfoId == null){
                if (StringUtils.isBlank(accountInfo.getCategoryName()) || accountInfo.getPrePrice() == null || accountInfo.getAccountRelPrice() == null)
                    return ServerResponse.createByErrorMessage("参数错误");

                if (accountInfo.getItemId() == null){
                    List<User> list = userMapper.selectByUserType(UserAuth.FINANCIAL.getCode());
                    accountInfo.setCheckUserName(list.get(0).getUserName());
                }
                else {
                    User checkUser = userMapper.selectByUserTypeAndItemId(UserAuth.ACCOUNT_CHECKED.getCode(),accountInfo.getItemId());
                    accountInfo.setCheckUserName(checkUser.getUserName());
                }
                accountInfo.setAccountImg(PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
                accountInfo.setState(Const.Account.UPLOAD_AUDITING);
                if (accountInfo.getAccountInfoCode() == null){
                    User user = userMapper.selectByPrimaryKey(accountInfo.getUserId());
                    accountInfo.setAccountInfoCode(user.getAccountInfoCode());
                }
                int row = accountInfoMapper.insert(accountInfo);
                if (row > 0)
                    return ServerResponse.createBySuccess("上传成功");
                return ServerResponse.createByErrorMessage("上传失败");
            }


            else {
                if (accountInfo.getPrePrice() == null || accountInfo.getAccountRelPrice() == null)
                    return ServerResponse.createByErrorMessage("参数错误");
                MaterialBuyInfo materialBuyInfo = materialBuyInfoMapper.selectByPrimaryKey(materialInfoId);

                accountInfo.setCategoryName(materialBuyInfo.getCategoryName());
                accountInfo.setItemId(materialBuyInfo.getItemId());
                User checkUser = userMapper.selectByUserTypeAndItemId(UserAuth.ACCOUNT_CHECKED.getCode(),accountInfo.getItemId());
                accountInfo.setCheckUserName(checkUser.getUserName());
                accountInfo.setAccountImg(PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
                accountInfo.setState(Const.Account.UPLOAD_AUDITING);

                if (accountInfo.getAccountInfoCode() == null){
                    User user = userMapper.selectByPrimaryKey(accountInfo.getUserId());
                    accountInfo.setAccountInfoCode(user.getAccountInfoCode());
                }
                int row = accountInfoMapper.insert(accountInfo);
                if (row > 0){
                    materialBuyInfo.setAccountInfoId(accountInfo.getAccountInfoId());
                    materialBuyInfoMapper.updateByPrimaryKeySelective(materialBuyInfo);
                    return ServerResponse.createBySuccess("上传成功");
                }
                return ServerResponse.createByErrorMessage("上传失败");
            }
        }
        return ServerResponse.createByErrorMessage("图片上传失败");
    }

    public ServerResponse<PageInfo<AccountInfo>> checkUserList(int pageSize, int pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<AccountInfo> list = accountInfoMapper.selectUncheckListNotItemId();
        PageInfo<AccountInfo> pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<PageInfo<AccountInfo>> checkUserList(int pageSize, int pageNum, Integer itemId){
        PageHelper.startPage(pageNum,pageSize);
        List<AccountInfo> list = accountInfoMapper.selectUncheckList(itemId);
        PageInfo<AccountInfo> pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse checkUserConfirm(Integer accountInfoId){
        if (accountInfoId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountInfoId(accountInfoId);
        accountInfo.setState(Const.Account.CHECK_AUDITING);
        int row = accountInfoMapper.updateByPrimaryKeySelective(accountInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("审核成功");
        return ServerResponse.createByErrorMessage("审核失败");
    }

    public ServerResponse<PageInfo<AccountInfo>> userList(int pageSize, int pageNum,Integer userId, Integer itemId){
        PageHelper.startPage(pageNum,pageSize);
        List<AccountInfo> list = accountInfoMapper.selectCheckList(itemId,userId);
        PageInfo<AccountInfo> pageInfo = new PageInfo<>(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<PageInfo<AccountInfo>> userList(int pageSize, int pageNum,Integer userId){
        PageHelper.startPage(pageNum,pageSize);
        List<AccountInfo> list = accountInfoMapper.selectCheckListNotItemId(userId);
        PageInfo<AccountInfo> pageInfo = new PageInfo<>(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse userConfirm(Integer accountInfoId){
        if (accountInfoId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountInfoId(accountInfoId);
        accountInfo.setState(Const.Account.FINISHED);
        int row = accountInfoMapper.updateByPrimaryKeySelective(accountInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("确认成功");
        return ServerResponse.createByErrorMessage("确认失败");
    }

    public ServerResponse<AccountInfo> getAccountById(Integer accountInfoId){
        if (accountInfoId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        AccountInfo accountInfo = accountInfoMapper.selectByPrimaryKey(accountInfoId);
        if (accountInfo == null)
            return ServerResponse.createByErrorMessage("找不到信息");
        return ServerResponse.createBySuccess(accountInfo);
    }

    public ServerResponse<PageInfo<AccountInfo>> getItemAccountList(int pageSize, int pageNum, Integer itemId){
        PageHelper.startPage(pageNum,pageSize);
        List<AccountInfo> list = accountInfoMapper.selectItemAccountList(itemId);
        PageInfo<AccountInfo> pageInfo = new PageInfo<>(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public ServerResponse<PageInfo<AccountInfo>> getAccountList(int pageSize, int pageNum){
        PageHelper.startPage(pageNum,pageSize);
        List<AccountInfo> list = accountInfoMapper.selectAccountList();
        PageInfo<AccountInfo> pageInfo = new PageInfo<>(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<UserAccountVo> getAccount() {

        UserAccountVo userAccountVo = new UserAccountVo();
        userAccountVo.setDayPayAccount(accountInfoMapper.selectAllPayAccountDayCompany());
        userAccountVo.setDayIncomeAccount(accountInfoMapper.selectAllIncomeAccountDayCompany());
        userAccountVo.setMonthPayAccount(accountInfoMapper.selectAllPayAccountMonthCompany());
        userAccountVo.setMonthIncomeAccount(accountInfoMapper.selectAllIncomeAccountMonthCompany());
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


    @Override
    public ServerResponse<List<AccountInfo>> getAccountListByTime(String startTime, String endTime) {
        if (startTime == null || endTime == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        List<AccountInfo> list = accountInfoMapper.selectByTime(startTime,endTime);
        return ServerResponse.createBySuccess(list);
    }
}
