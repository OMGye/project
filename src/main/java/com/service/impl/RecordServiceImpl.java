package com.service.impl;

import com.common.Const;
import com.common.RecordAuth;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.dao.ItemMapper;
import com.dao.OfferMaterialMapper;
import com.dao.RecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pojo.*;
import com.service.RecordService;
import com.util.*;
import com.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    private OfferMaterialMapper offerMaterialMapper;

    @Override
    public ServerResponse addRecord(User user,Record record, String path, MultipartFile[] files) {
        if (record.getRecordType() == null || StringUtils.isBlank(record.getRecordDec()) || record.getItemId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        if(record.getUnitPrice() == null && record.getNumber() == null && record.getSumPrice() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        if (files != null && files.length > 0) {
            List<File> list = new ArrayList<>();
            String imgName = "";
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                if (record.getRecordImgName() == null)
                    record.setRecordImgName(fileName+ ",");
                else
                    record.setRecordImgName(record.getRecordImgName()+fileName+ ",");
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
        if(record.getRecordImgName() != null)
            record.setRecordImgName(record.getRecordImgName().substring(0,record.getRecordImgName().length()-1));
        if (record.getUnitPrice() != null && record.getNumber() != null)
            record.setSumPrice(BigDecimalUtil.mul(record.getUnitPrice().doubleValue(),record.getNumber()));
        record.setUserId(user.getUserId());
        record.setUserName(user.getUserName());
        Item item = itemMapper.selectByPrimaryKey(record.getItemId());
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
    public ServerResponse addRecordImg(User user, Integer recordId, String path, MultipartFile file) {
        if (recordId == null || file == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Record record = recordMapper.selectByPrimaryKey(recordId);
        if (record == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if (record.getState() == Const.RecordConst.Last_CHECK)
            return ServerResponse.createByErrorMessage("审核完成，不能再次进行修改");
        if (record.getUserId() != user.getUserId())
            return ServerResponse.createByErrorMessage("您不是此条记录的上传者，不能进行修改");
        if(file != null){
            String fileName = file.getOriginalFilename();
            if (record.getRecordImgName() == null){
                record.setRecordImgName(fileName);
            }
            else {
                record.setRecordImgName(record.getRecordImgName() + "," + fileName);
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
            if (record.getRecordImgs() != null)
            record.setRecordImgs(record.getRecordImgs()+","+PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
            else
                record.setRecordImgs(PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
        }
        int rowCount = recordMapper.updateByPrimaryKeySelective(record);
        if (rowCount > 0)
            return ServerResponse.createBySuccessMessage("上传成功");
        return ServerResponse.createByErrorMessage("上传失败");
    }

    @Override
    public ServerResponse deleteRecordImg(User user, Integer recordId, String fileName, String name) {
        if (recordId == null && fileName == null && name == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Record record = recordMapper.selectByPrimaryKey(recordId);
        if (record == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if (record.getState() == Const.RecordConst.Last_CHECK)
            return ServerResponse.createByErrorMessage("审核完成，不能再次进行修改");
        if (record.getUserId() != user.getUserId())
            return ServerResponse.createByErrorMessage("您不是此条记录的上传者，不能进行修改");
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
                if (!imgs[i].equals(""))
                    sb.append(",");
        }
        String[] imgNames = record.getRecordImgName().split(",");
        for (int i = 0; i < imgNames.length; i++)
            if (imgs[i].equals(name)) {
                imgs[i] = "";
                break;
            }
        StringBuilder sbName = new StringBuilder();
        for (int i = 0; i < imgNames.length; i++){
            sbName.append(imgs[i]);
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
        record.setRecordImgName(name);
        record.setRecordImgs(fileName);
        int rowCount = recordMapper.updateByPrimaryKeySelective(record);
        if (rowCount > 0)
            return ServerResponse.createBySuccessMessage("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse<PageInfo> list(User user, Integer state, Integer type, int pageSize, int pageNum,Integer itemId) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("record_id desc");
        List<Integer> itemList = new ArrayList<>();
        Integer userId = null;
        if (user.getUserType() == UserAuth.ITEM_UPLOAD.getCode()){
            userId = user.getUserId();
            List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
            itemList.add(list.get(0).getItemId());
        }
        if (user.getUserType() == UserAuth.MANAGER.getCode()){
            if (itemId == null){
                List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
                for (ItemIndexVo itemIndexVo : list)
                    itemList.add(itemIndexVo.getItemId());
            }
            else
                itemList.add(itemId);

        }

        List<Record> list = recordMapper.selectlist(state,type,itemList,userId);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse update(User user, Record record) {
        if (record.getRecordId() == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Record selectRecord = recordMapper.selectByPrimaryKey(record.getRecordId());
        if (selectRecord == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if (selectRecord.getState() == Const.RecordConst.Last_CHECK)
            return ServerResponse.createByErrorMessage("审核完成，不能再次进行修改");
        if (selectRecord.getUserId() != user.getUserId())
            return ServerResponse.createByErrorMessage("您不是此条记录的上传者，不能进行修改");
        record.setState(null);
        record.setItemId(null);
        int rowCount = recordMapper.updateByPrimaryKeySelective(record);
        if (rowCount > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createBySuccess("修改失败");
    }

    @Override
    public ServerResponse managerCheck(User user, Integer recordId) {
        if (recordId == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Record record = recordMapper.selectByPrimaryKey(recordId);
        if (record == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        boolean isEixst = false;
        List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
        for (ItemIndexVo itemIndexVo : list){
            if (itemIndexVo.getItemId() == record.getItemId())
                isEixst = true;
        }
        if (!isEixst)
            return ServerResponse.createByErrorMessage("您不能审核该记录");
        record.setState(Const.RecordConst.FIRST_CHECK);
        int rowCount = recordMapper.updateByPrimaryKeySelective(record);
        if (rowCount > 0)
            return ServerResponse.createBySuccess("审核成功");
        return ServerResponse.createByErrorMessage("审核失败");
    }

    @Override
    public ServerResponse<PageInfo> AllList(Integer itemId, Integer state, Integer type, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("record_id desc");
        List<Record> list = recordMapper.selectlistAll(state,type,itemId,null);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse financialCheck(Integer recordId) {
        if (recordId == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Record record = recordMapper.selectByPrimaryKey(recordId);
        if (record == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if (record.getState() != Const.RecordConst.FIRST_CHECK)
            return ServerResponse.createByErrorMessage("该条记录不能审核");
        record.setState(Const.RecordConst.Last_CHECK);
        int rowCount = recordMapper.updateByPrimaryKeySelective(record);
        if (rowCount > 0)
            return ServerResponse.createBySuccess("审核成功");
        return ServerResponse.createByErrorMessage("审核失败");
    }

    @Override
    public ServerResponse getByRecordId(User user,Integer recordId) {
        if (recordId == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Record record = recordMapper.selectByPrimaryKey(recordId);
        if (record == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if (user.getUserType() == UserAuth.ITEM_UPLOAD.getCode())
            if (record.getUserId() != user.getUserId())
                return ServerResponse.createByErrorMessage("您无权访问该条记录");
        if (user.getUserType() == UserAuth.MANAGER.getCode()) {
            boolean isEixst = false;
            List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
            for (ItemIndexVo itemIndexVo : list) {
                if (itemIndexVo.getItemId() == record.getItemId())
                    isEixst = true;
            }
            if (!isEixst)
                return ServerResponse.createByErrorMessage("您无权访问该条记录");
        }
        RecordVo recordVo = new RecordVo();
        BeanUtils.copyProperties(record,recordVo);
        if (record.getOfferId() != null){
           OfferMaterial offerMaterial = offerMaterialMapper.selectByPrimaryKey(record.getOfferId());
           recordVo.setOfferName(offerMaterial.getOfferCompany());
        }
        return ServerResponse.createBySuccess(recordVo);
    }

    @Override
    public ServerResponse refuseRecord(User user, Integer recordId, String recordRefuse) {
        if (recordId == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Record record = recordMapper.selectByPrimaryKey(recordId);
        if (record == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if (user.getUserType() == UserAuth.MANAGER.getCode()) {
            boolean isEixst = false;
            List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
            for (ItemIndexVo itemIndexVo : list) {
                if (itemIndexVo.getItemId() == record.getItemId())
                    isEixst = true;
            }
            if (!isEixst)
                return ServerResponse.createByErrorMessage("您无权拒绝该条记录");
        }
        record.setState(Const.RecordConst.RECORD_REFUSE);
        String str = "";
        if (user.getUserType() == UserAuth.FINANCIAL.getCode()){
             str = "审核职位：财务管理员 br/ 审核人："+user.getUserName()+ "br/" + recordRefuse;
        }
        if (user.getUserType() == UserAuth.MANAGER.getCode()){
            str = "审核职位：项目经理 br/ 审核人："+user.getUserName()+ "br/" + recordRefuse;
        }
        record.setRecordRefuse(str);
        int rowCount = recordMapper.updateByPrimaryKeySelective(record);
        if (rowCount > 0)
            return ServerResponse.createBySuccess("拒绝成功");
        return ServerResponse.createByErrorMessage("拒绝失败");
    }

    @Override
    public ServerResponse<PageInfo> listByDec(User user, Integer state, String recordDec, int pageSize, int pageNum) {
        if (recordDec == null)
            return ServerResponse.createByErrorMessage("参数为空");
        recordDec = "%" + recordDec + "%";
        List<Integer> itemList = new ArrayList<>();
        if (user.getUserType() == UserAuth.ITEM_UPLOAD.getCode()){
            List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
            itemList.add(list.get(0).getItemId());
        }

        if (user.getUserType() == UserAuth.MANAGER.getCode()){
            List<ItemIndexVo> list = JsonUtil.toJsonList(user.getItemId());
            for (ItemIndexVo itemIndexVo : list)
                itemList.add(itemIndexVo.getItemId());
        }
        if (itemList.size() == 0)
            itemList = null;
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("record_id desc");
        Integer userId = null;
        if (user.getUserType() == UserAuth.ITEM_UPLOAD.getCode())
            userId = user.getUserId();
        List<Record> list = recordMapper.selectlistByDec(state,recordDec,itemList,userId);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse deleteByRecordId(User user, Integer recordId) {
        if (recordId == null)
            return ServerResponse.createByErrorMessage("没有传递参数");
        Record record = recordMapper.selectByPrimaryKey(recordId);
        if (record == null)
            return ServerResponse.createByErrorMessage("没有该条记录");
        if (user.getUserType() == UserAuth.BOSS.getCode()){
            int row = recordMapper.deleteByPrimaryKey(recordId);
            if (row > 0)
                return ServerResponse.createBySuccess("删除成功");
            return ServerResponse.createByErrorMessage("删除失败");
        }
        if (user.getUserType() == UserAuth.ITEM_UPLOAD.getCode())
            if (record.getState() == Const.RecordConst.FIRST_CHECK)
                return ServerResponse.createByErrorMessage("该记录已被审核，不能删除");
        if (user.getUserType() == UserAuth.MANAGER.getCode())
            if (record.getState() == Const.RecordConst.Last_CHECK)
                return ServerResponse.createByErrorMessage("该记录已被审核，不能删除");
        int row = recordMapper.deleteByPrimaryKey(recordId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse<PageInfo> listByOfferId(User user, Integer state, Integer offerId, int pageSize, int pageNum) {
        if (offerId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("record_id desc");
        List<Record> list = recordMapper.selectByOfferId(state,offerId);
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<RecordAmountVo> getRecordAmount(Integer itemId, Integer offerId) {

        List<RecordDecNum> list = recordMapper.selectAmountMaterial(itemId,offerId,RecordAuth.MATERIAL.getCode(), Const.RecordConst.Last_CHECK);
        RecordAmountVo recordAmountVo = new RecordAmountVo();
        recordAmountVo.setList(list);
        BigDecimal bigDecimal = recordMapper.selectAmountPrice(itemId,offerId,Const.RecordConst.Last_CHECK);
        BigDecimal materialBigDecimal = recordMapper.selectMaterialAmountPrice(itemId,offerId,Const.RecordConst.Last_CHECK,RecordAuth.MATERIAL.getCode());
        recordAmountVo.setMaterailSumPrice(materialBigDecimal);
        recordAmountVo.setSumPrice(bigDecimal);
        return ServerResponse.createBySuccess(recordAmountVo);
    }

    @Override
    public XSSFWorkbook exportExcelInfo(Integer itemId, Integer type, Integer offerId) {
        XSSFWorkbook xssfWorkbook=null;
        try {
            List<Record> recordList = null;
            //根据ID查找数据
            if (offerId == null) {
                recordList = recordMapper.selectlistAll(Const.RecordConst.Last_CHECK,type,itemId,null);
            }
            else {
                recordList = recordMapper.selectByOfferId(Const.RecordConst.Last_CHECK,offerId);
            }
            List<RecordVo> recordVoList = new ArrayList<>();
            for (Record record : recordList){
                RecordVo recordVo = new RecordVo();
                BeanUtils.copyProperties(record,recordVo);
                if (record.getOfferId() != null){
                    OfferMaterial offerMaterial = offerMaterialMapper.selectByPrimaryKey(record.getOfferId());
                    if (offerMaterial != null)
                        recordVo.setOfferName(offerMaterial.getOfferCompany());
                }
                recordVoList.add(recordVo);
            }

            List<ExcelBean> excel=new ArrayList<>();
            Map<Integer,List<ExcelBean>> map=new LinkedHashMap<>();

            //设置标题栏
            excel.add(new ExcelBean("记录id","recordId",0));
            excel.add(new ExcelBean("记录员名称","userName",0));
            excel.add(new ExcelBean("项目名称","itemName",0));
            excel.add(new ExcelBean("记录描述","recordDec",0));
            excel.add(new ExcelBean("供应商","offerName",0));
            excel.add(new ExcelBean("供应车号","recordCarNumber",0));
            excel.add(new ExcelBean("司机","recordCarOffer",0));
            excel.add(new ExcelBean("单价","unitPrice",0));
            excel.add(new ExcelBean("数量","number",0));
            excel.add(new ExcelBean("总价","sumPrice",0));
            excel.add(new ExcelBean("创建时间","createTime",0));
            map.put(0, excel);
            String sheetName ="RecordInfo";
            xssfWorkbook = ExcelUtil.createExcelFile(RecordVo.class, recordVoList, map, sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xssfWorkbook;
    }

    @Override
    public ServerResponse<IndexVo> getIndexVo() {

        List<Item> itemList = itemMapper.select();
        List<OfferMaterial> offerMaterialList = offerMaterialMapper.selectList();
        IndexVo indexVo = new IndexVo();
        List<ItemIndexVo> itemIndexVoList = new ArrayList<>();
        List<OfferMaterialIndexVo> offerMaterialIndexVoList = new ArrayList<>();
        for (Item item : itemList){
            ItemIndexVo itemIndexVo = new ItemIndexVo();
            BeanUtils.copyProperties(item,itemIndexVo);
            itemIndexVoList.add(itemIndexVo);
        }
        for (OfferMaterial offerMaterial : offerMaterialList){
            OfferMaterialIndexVo offerMaterialIndexVo = new OfferMaterialIndexVo();
            BeanUtils.copyProperties(offerMaterial,offerMaterialIndexVo);
            offerMaterialIndexVoList.add(offerMaterialIndexVo);
        }
        indexVo.setItemIndexVoList(itemIndexVoList);
        indexVo.setOfferMaterialIndexVoList(offerMaterialIndexVoList);
        return ServerResponse.createBySuccess(indexVo);
    }


}
