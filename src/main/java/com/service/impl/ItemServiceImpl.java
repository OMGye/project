package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.common.Const;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.dao.ItemMapper;
import com.dao.RecordMapper;
import com.dao.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pojo.Item;
import com.pojo.Record;
import com.pojo.User;
import com.service.ItemService;
import com.util.FTPUtil;
import com.util.JsonUtil;
import com.util.PropertiesUtil;
import com.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by upupgogogo on 2018/8/2.上午2:43
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RecordMapper recordMapper;

    private Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Transactional
    public ServerResponse addNewItem(Item item, MultipartFile[] files, String path, String endTimeString){
        if(StringUtils.isBlank(item.getItemName()) || StringUtils.isBlank(item.getItemDec()))
            return ServerResponse.createByErrorMessage("参数为空");
        Item selectItem = itemMapper.selectByItemName(item.getItemName());
        if (selectItem != null){
            return ServerResponse.createByErrorMessage("项目名已经存在");
        }
        item.setState(Const.Item.WORKING);
        if(StringUtils.isNotBlank(endTimeString)) {
            try {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sDateFormat.parse(endTimeString);
                item.setEndTime(date);
            } catch (Exception ex) {
                return ServerResponse.createByErrorMessage("endTime参数错误");
            }
        }
        if (files != null && files.length > 0) {
            List<File> list = new ArrayList<>();
            String imgName = "";
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                if (item.getItemFileName() == null)
                    item.setItemFileName(fileName+ ",");
                else
                    item.setItemFileName(item.getItemFileName()+fileName+ ",");
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
                list.add(targetFile);
                try {
                    file.transferTo(targetFile);
                } catch (IOException e) {
                    logger.error("上传文件异常", e);
                    return null;
                }
            }

            try {

                //文件已经上传成功了


                FTPUtil.uploadFile(list);
                //已经上传到ftp服务器上

                for (int i = 0; i < list.size(); i ++){
                    list.get(i).delete();
                    if (i != list.size() - 1)
                        imgName += PropertiesUtil.getProperty("ftp.server.http.prefix") + list.get(i).getName() + ",";
                    else
                        imgName += PropertiesUtil.getProperty("ftp.server.http.prefix") + list.get(i).getName();
                }
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                return null;
            }
            item.setItemFile(imgName);
        }
        Integer rowCount = itemMapper.insert(item);
        if (rowCount <= 0)
            return ServerResponse.createByErrorMessage("新建项目失败");
        if (item.getItemManagerId() != null){
            User user = userMapper.selectByPrimaryKey(item.getItemManagerId());
            if (user != null){
                if (user.getItemId() == null)
                {
                    List<ItemIndexVo> list = new ArrayList<>();
                    list.add(new ItemIndexVo(item.getItemId(),item.getItemName()));
                    userMapper.updateItemId(item.getItemManagerId(), JsonUtil.toJonSting(list));
                }
                else
                {
                    List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
                    list.add(new ItemIndexVo(item.getItemId(),item.getItemName()));
                    userMapper.updateItemId(item.getItemManagerId(), JsonUtil.toJonSting(list));

                }
            }
        }

        if (item.getItemUploaderId() != null){
            List<ItemIndexVo> list = new ArrayList<>();
            list.add(new ItemIndexVo(item.getItemId(),item.getItemName()));
            userMapper.updateItemId(item.getItemUploaderId(), JsonUtil.toJonSting(list));
        }


        return ServerResponse.createBySuccess("新建项目成功",item);
    }

    @Override
    public ServerResponse addItemFile(Integer itemId, String path, MultipartFile file) {
        if (itemId == null || file == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Item item = itemMapper.selectByPrimaryKey(itemId);
        if (item == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if(file != null){
            String fileName = file.getOriginalFilename();
            if (item.getItemFileName() == null){
                item.setItemFileName(fileName);
            }
            else {
                item.setItemFileName(item.getItemFileName() + "," + fileName);
            }
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
            if (item.getItemFile() != null || item.getItemFile().equals(""))
            item.setItemFile(item.getItemFile()+","+PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
            else
                item.setItemFile(PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
        }
        int rowCount = itemMapper.updateByPrimaryKeySelective(item);
        if (rowCount > 0)
            return ServerResponse.createBySuccessMessage("上传成功");
        return ServerResponse.createByErrorMessage("上传失败");
    }

    @Override
    public ServerResponse deleteItemFile(Integer itemId, String fileName, String name) {
        if (itemId == null && fileName == null && name == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Item item = itemMapper.selectByPrimaryKey(itemId);
        if (item == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if (item.getItemFileName() == null || item.getItemFileName().isEmpty())
            return ServerResponse.createByErrorMessage("没有该文件");
        String[] imgs = item.getItemFile().split(",");
        for (int i = 0; i < imgs.length; i++)
            if (imgs[i].equals(fileName)) {
                imgs[i] = "";
                break;
            }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < imgs.length; i++){
            sb.append(imgs[i]);
            if (i != imgs.length - 1)
                if (!imgs[i].equals(""))
                    sb.append(",");
        }
        String[] imgNames = item.getItemFileName().split(",");
        for (int i = 0; i < imgNames.length; i++)
            if (imgNames[i].equals(name)) {
                imgNames[i] = "";
                break;
            }
        StringBuilder sbName = new StringBuilder();
        for (int i = 0; i < imgNames.length; i++){
            sbName.append(imgNames[i]);
            if (i != imgNames.length - 1)
                if (!imgNames[i].equals(""))
                    sbName.append(",");
        }
        fileName = sb.toString();
        if (fileName.length() > 0 && fileName.charAt(fileName.length() - 1) == ',')
            fileName = fileName.substring(0,fileName.length() - 1);
        name = sbName.toString();
        if (name.length() > 0 && name.charAt(name.length() - 1) == ',')
            name = name.substring(0,name.length() - 1);
        item.setItemFileName(name);
        item.setItemFile(fileName);
        int rowCount = itemMapper.updateByPrimaryKeySelective(item);
        if (rowCount > 0)
            return ServerResponse.createBySuccessMessage("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse<PageInfo<Item>> listItem(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("item_id desc");
        List<Item> list = itemMapper.select();
        PageInfo<Item> pageInfo = new PageInfo(list);
        pageInfo.setList(list);
        return ServerResponse.createBySuccess(pageInfo);
    }


    @Override
    public ServerResponse<List<UserVo>> getUserForCheck(Integer userType) {
        if (userType == UserAuth.ITEM_UPLOAD.getCode()) {
            List<User> list = userMapper.selectByUserType(userType);
            List<UserVo> userVoList = new ArrayList<>();
            for (User user : list) {
                UserVo userVo = new UserVo(user.getUserId(), user.getUserName());
                userVoList.add(userVo);
            }

            return ServerResponse.createBySuccess(userVoList);
        }
        else {

            List<User> list = userMapper.selectByUserTypeIsManager(userType);
            List<UserVo> userVoList = new ArrayList<>();
            for (User user : list) {
                UserVo userVo = new UserVo(user.getUserId(), user.getUserName());
                userVoList.add(userVo);
            }

            return ServerResponse.createBySuccess(userVoList);
        }
    }


    @Override
    @Transactional
    public ServerResponse updateItem(Item item, MultipartFile file, String path, String endTime){
        if (item.getItemId() == null)
            return ServerResponse.createByErrorMessage("参数错误");
        if(StringUtils.isNotBlank(endTime)) {
            try {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sDateFormat.parse(endTime);
                item.setEndTime(date);
            } catch (Exception ex) {
                return ServerResponse.createByErrorMessage("endTime参数错误");
            }
        }

        if(file != null){
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
            item.setItemFile(PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
        }

        Item fistItem = itemMapper.selectByPrimaryKey(item.getItemId());
        if (fistItem.getItemUploaderId() == null){
            if (item.getItemUploaderId() != null){
                List<ItemIndexVo> list = new ArrayList<>();
                list.add(new ItemIndexVo(fistItem.getItemId(),fistItem.getItemName()));
                userMapper.updateItemId(item.getItemUploaderId(), JsonUtil.toJonSting(list));
            }
        }
        if (fistItem.getItemUploaderId() != null && item.getItemUploaderId() != null){
            userMapper.updateUploadByItemId(fistItem.getItemUploaderId());
            List<ItemIndexVo> list = new ArrayList<>();
            list.add(new ItemIndexVo(fistItem.getItemId(),fistItem.getItemName()));
            userMapper.updateItemId(item.getItemUploaderId(), JsonUtil.toJonSting(list));
        }


        if (fistItem.getItemManagerId() == null){
            if (item.getItemManagerId() != null){
                User user = userMapper.selectByPrimaryKey(item.getItemManagerId());
                if (user != null){
                    if (user.getItemId() == null)
                    {
                        List<ItemIndexVo> list = new ArrayList<>();
                        list.add(new ItemIndexVo(fistItem.getItemId(),fistItem.getItemName()));
                        userMapper.updateItemId(item.getItemManagerId(), JsonUtil.toJonSting(list));
                    }
                    else
                    {
                        List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
                        list.add(new ItemIndexVo(fistItem.getItemId(),fistItem.getItemName()));
                        userMapper.updateItemId(item.getItemManagerId(), JsonUtil.toJonSting(list));

                    }
                }
            }
        }

        if (fistItem.getItemManagerId() != null && item.getItemManagerId() != null) {
            User user = userMapper.selectByPrimaryKey(fistItem.getItemManagerId());
            List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getItemId() == fistItem.getItemId()) {
                    list.remove(i);
                }
            }

            if (list.size() == 0){
                user.setItemId(null);
                userMapper.updateManagerByItemId(user.getUserId());
            }
            else {
                user.setItemId(JsonUtil.toJonSting(list));
                userMapper.updateByPrimaryKeySelective(user);
            }

            user = userMapper.selectByPrimaryKey(item.getItemManagerId());
            if (user != null) {
                if (user.getItemId() == null) {
                    list = new ArrayList<>();
                    list.add(new ItemIndexVo(fistItem.getItemId(), fistItem.getItemName()));
                    userMapper.updateItemId(item.getItemManagerId(), JsonUtil.toJonSting(list));
                } else {
                    list = JsonUtil.toJsonList(user.getItemId());
                    list.add(new ItemIndexVo(fistItem.getItemId(), fistItem.getItemName()));
                    userMapper.updateItemId(item.getItemManagerId(), JsonUtil.toJonSting(list));

                }
            }
        }

        itemMapper.updateByPrimaryKeySelective(item);

        return ServerResponse.createBySuccess("修改成功");
    }


    @Override
    public ServerResponse<PageInfo<Item>> getItemByName(int pageNum, int pageSize, String itemName) {
        PageHelper.startPage(pageNum,pageSize);
        if (itemName == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        PageHelper.orderBy("item_id desc");
        itemName = "%" + itemName + "%";
        List<Item> list = itemMapper.selectByName(itemName);
        PageInfo<Item> pageInfo = new PageInfo(list);
        pageInfo.setList(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<Item> getItemById(Integer itemId) {
        if (itemId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        Item item = itemMapper.selectByPrimaryKey(itemId);
        return ServerResponse.createBySuccess(item);
    }

    @Override
    public ServerResponse delete(Integer itemId) {
        Item item = itemMapper.selectByPrimaryKey(itemId);
        if (item == null)
            return ServerResponse.createByErrorMessage("没有该项目");
        if (item.getItemManagerId() != null) {
            User user = userMapper.selectByPrimaryKey(item.getItemManagerId());
            if (user != null) {
                List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getItemId() == item.getItemId()) {
                        list.remove(i);
                    }
                }

                if (list.size() == 0) {
                    user.setItemId(null);
                    userMapper.updateManagerByItemId(user.getUserId());
                } else {
                    user.setItemId(JsonUtil.toJonSting(list));
                    userMapper.updateByPrimaryKeySelective(user);
                }
            }
        }
        if (item.getItemUploaderId() != null) {
            User user = userMapper.selectByPrimaryKey(item.getItemUploaderId());
            if (user != null){
                user.setItemId(null);
                userMapper.updateByPrimaryKey(user);
            }
        }
        int row = itemMapper.deleteByPrimaryKey(itemId);
        recordMapper.deleteByItemId(itemId);
        return ServerResponse.createBySuccess("删除成功");
    }

    public static void main(String[] args) {
        List<ItemIndexVo> list = JsonUtil.toJsonList("[{'itemId':74,'itemName':'testOne'}]");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getItemId() == 74) {
                list.remove(i);
            }
        }
        System.out.println(list.size());
    }
}
