package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.dao.ItemMapper;
import com.dao.RecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pojo.Item;
import com.pojo.Record;
import com.pojo.User;
import com.service.RecordService;
import com.util.FTPUtil;
import com.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by upupgogogo on 2018/9/4.下午1:48
 */
@Service("recordService")
public class RecordServiceImpl implements RecordService{

    private Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public ServerResponse addRecord(User user,Record record, String path, MultipartFile[] files) {
        if (record.getRecordType() == null || StringUtils.isBlank(record.getRecordDec()) || record.getUnitPrice() == null || record.getNumber() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        if (files != null && files.length > 0) {
            List<File> list = new ArrayList<>();
            String imgName = "";
            for (MultipartFile file : files) {
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
            record.setRecordImgs(imgName);
        }
        record.setUserId(user.getUserId());
        record.setUserName(user.getUserName());
        record.setItemId(user.getItemId());
        Item item = itemMapper.selectByPrimaryKey(user.getItemId());
        record.setItemName(item.getItemName());
        if(user.getUserType() == UserAuth.ITEM_UPLOAD.getCode())
            record.setState(Const.RecordConst.UNCHECK);
        else
            record.setState(Const.RecordConst.FIRST_CHECK);
        int rowCount = recordMapper.insert(record);
        if (rowCount > 0)
            return ServerResponse.createBySuccessMessage("上传成功");
        return ServerResponse.createByErrorMessage("上传失败");
    }

    @Override
    public ServerResponse addRecordImg(Integer recordId, String path, MultipartFile file) {
        if (recordId == null || file == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Record record = recordMapper.selectByPrimaryKey(recordId);
        if (record == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if (record.getState() == Const.RecordConst.Last_CHECK)
            return ServerResponse.createByErrorMessage("审核完成，不能再次进行修改");
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
            record.setRecordImgs(record.getRecordImgs()+","+PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
        }
        int rowCount = recordMapper.updateByPrimaryKeySelective(record);
        if (rowCount > 0)
            return ServerResponse.createBySuccessMessage("上传成功");
        return ServerResponse.createByErrorMessage("上传失败");
    }

    @Override
    public ServerResponse deleteRecordImg(Integer recordId, String fileName) {
        if (recordId == null && fileName == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Record record = recordMapper.selectByPrimaryKey(recordId);
        if (record == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if (record.getState() == Const.RecordConst.Last_CHECK)
            return ServerResponse.createByErrorMessage("审核完成，不能再次进行修改");
        if (record.getRecordImgs() == null || record.getRecordImgs().isEmpty())
            return ServerResponse.createByErrorMessage("没有该文件");
        String[] imgs = record.getRecordImgs().split(",");
        for (int i = 0; i < imgs.length; i++)
            if (imgs[i].equals(fileName)) {
                imgs[i] = "";
                break;
            }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < imgs.length; i++){
            sb.append(imgs[i]);
            if (i != imgs.length - 1)
                sb.append(",");
        }
        record.setRecordImgs(sb.toString());
        int rowCount = recordMapper.updateByPrimaryKeySelective(record);
        if (rowCount > 0)
            return ServerResponse.createBySuccessMessage("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse<PageInfo<Record>> list(User user, Integer state, Integer type, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("record_id desc");
        List<Record> list = recordMapper.selectlist(state,type,user.getItemId());
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
