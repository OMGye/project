package com.service.impl;

import com.common.Const;
import com.common.ServerResponse;
import com.common.UserAuth;
import com.dao.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.pojo.*;
import com.service.MaterialService;
import com.util.FTPUtil;
import com.util.PropertiesUtil;
import com.vo.ItemMaterialDetailVo;
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
 * Created by upupgogogo on 2018/8/6.下午4:04
 */
@Service("materialService")
public class MaterialServiceImpl implements MaterialService{

    private Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MaterialBuyInfoMapper materialBuyInfoMapper;

    @Autowired
    private MaterialStockMapper materialStockMapper;

    @Autowired
    private MaterialUseInfoMapper materialUseInfoMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public ServerResponse buyMaterial(MaterialBuyInfo materialBuyInfo, MultipartFile file, String path) {
        if (file == null)
            return ServerResponse.createByErrorMessage("必须传凭证");
        if (materialBuyInfo.getCategoryName() == null || materialBuyInfo.getOfferId() == null || materialBuyInfo.getSellerName() == null || materialBuyInfo.getUnitPrice() == null || materialBuyInfo.getNumber() == null )
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
            return ServerResponse.createByErrorMessage("上传文件异常");
        }
        materialBuyInfo.setMaterialInfoImg(PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName());
        materialBuyInfo.setState(Const.Material.AUDITING);
        User user = userMapper.selectByUserTypeAndItemId(UserAuth.MATERIAL_CHECKED.getCode(),materialBuyInfo.getItemId());
        if (user == null)
            return ServerResponse.createByErrorMessage("没有找到该项目材料审核员");
        materialBuyInfo.setCheckUserName(user.getUserName());
        BigDecimal number = new BigDecimal(materialBuyInfo.getNumber());
        materialBuyInfo.setTotalPrice(number.multiply(materialBuyInfo.getUnitPrice()));
        int row = materialBuyInfoMapper.insert(materialBuyInfo);
        if (row > 0)
            return ServerResponse.createBySuccess("上传材料流水成功");

        return ServerResponse.createByErrorMessage("上传材料流水失败");
    }

    @Override
    public ServerResponse<List<MaterialStock>> getMaterialStockByItemId(Integer itemId) {
        List<MaterialStock> list = materialStockMapper.selectByItemId(itemId);
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse useMaterial(MaterialUseInfo materialUseInfo) {
        if (materialUseInfo.getCategoryName() == null || materialUseInfo.getNumber() == null )
            return ServerResponse.createByErrorMessage("参数错误");

        User user = userMapper.selectByUserTypeAndItemId(UserAuth.MATERIAL_CHECKED.getCode(),materialUseInfo.getItemId());
        if (user == null)
            return ServerResponse.createByErrorMessage("没有找到该项目材料审核员");

        materialUseInfo.setCheckUserName(user.getUserName());
        materialUseInfo.setNumber(materialUseInfo.getNumber() - 2*materialUseInfo.getNumber());
        materialUseInfo.setState(Const.Material.AUDITING);
        int row = materialUseInfoMapper.insert(materialUseInfo);
        if (row > 0){
            return ServerResponse.createBySuccess("上传材料流水成功");
        }
        return ServerResponse.createByErrorMessage("上传材料流水失败");
    }

    @Override
    public ServerResponse updateState(Integer materialUserId, Integer materialInfoId) {
        if (materialInfoId == null && materialUserId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        if (materialInfoId != null){
            MaterialBuyInfo materialBuyInfo = materialBuyInfoMapper.selectByPrimaryKey(materialInfoId);
            if (materialBuyInfo == null)
                return ServerResponse.createByErrorMessage("materialInfoId不存在");
            materialBuyInfo.setState(Const.Material.FINISHED);
            int row = materialBuyInfoMapper.updateByPrimaryKeySelective(materialBuyInfo);
            if (row > 0){
                MaterialStock materialStock = materialStockMapper.selectByCategoryName(materialBuyInfo.getCategoryName());
                if (materialStock != null){
                    materialStock.setSellStock(materialStock.getSellStock() + materialBuyInfo.getNumber());
                    materialStockMapper.updateByPrimaryKeySelective(materialStock);
                }
                else {
                    materialStock = new MaterialStock();
                    materialStock.setSellStock(materialBuyInfo.getNumber());
                    materialStock.setItemId(materialBuyInfo.getItemId());
                    materialStock.setUseStock(0);
                    materialStock.setCategoryName(materialBuyInfo.getCategoryName());
                    materialStock.setUnitPrice(materialBuyInfo.getUnitPrice());
                    materialStockMapper.insert(materialStock);
                }
                return ServerResponse.createBySuccess("审核通过");
            }
        }
        else {
            MaterialUseInfo materialUseInfo = materialUseInfoMapper.selectByPrimaryKey(materialUserId);
            if (materialUseInfo == null)
                return ServerResponse.createByErrorMessage("materialUserId不存在");
            materialUseInfo.setState(Const.Material.FINISHED);
            int row = materialUseInfoMapper.updateByPrimaryKeySelective(materialUseInfo);
            if (row > 0){
                MaterialStock materialStock = materialStockMapper.selectByCategoryName(materialUseInfo.getCategoryName());
                materialStock.setSellStock(materialStock.getUseStock() + materialUseInfo.getNumber());
                materialStockMapper.updateByPrimaryKeySelective(materialStock);
                return ServerResponse.createBySuccess("审核通过");
            }
        }

        return ServerResponse.createByErrorMessage("审核失败");
    }

    @Override
    public ServerResponse<List<MaterialBuyInfo>> getUncheckList(Integer itemId) {
        if (itemId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        List<MaterialBuyInfo> list = materialBuyInfoMapper.selectByItemId(itemId);
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse<List<MaterialUseInfo>> getUncheckUseList(Integer itemId) {
        if (itemId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        List<MaterialUseInfo> list = materialUseInfoMapper.selectByItemId(itemId);
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse getMaterialDetail(Integer materialUserId, Integer materialInfoId) {
        if (materialInfoId == null && materialUserId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        if (materialInfoId != null){
            MaterialBuyInfo materialBuyInfo = materialBuyInfoMapper.selectByPrimaryKey(materialInfoId);
            if (materialBuyInfo == null)
                return ServerResponse.createByErrorMessage("materialInfoId不存在");
            else
                return ServerResponse.createBySuccess(materialBuyInfo);
        }
        else {
            MaterialUseInfo materialUseInfo = materialUseInfoMapper.selectByPrimaryKey(materialUserId);
            if (materialUseInfo == null)
                return ServerResponse.createByErrorMessage("materialUserId不存在");
            else
                return ServerResponse.createBySuccess(materialUseInfo);
        }

    }

    @Override
    public ServerResponse<ItemMaterialDetailVo> getItemMaterialStockDetail(Integer itemId) {
        if (itemId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        Item item = itemMapper.selectByPrimaryKey(itemId);
        if (item != null){
            List<MaterialStock> list = materialStockMapper.selectByItemId(itemId);
            ItemMaterialDetailVo itemMaterialDetailVo = new ItemMaterialDetailVo(item,list);
            return ServerResponse.createBySuccess(itemMaterialDetailVo);
        }
        return ServerResponse.createByErrorMessage("找不到该项目");
    }

    @Override
    public ServerResponse<PageInfo<ItemMaterialDetailVo>> getListItemMaterialStockDetail(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<Item> list = itemMapper.select();
        if (list != null){
            List<ItemMaterialDetailVo> itemMaterialDetailVos = new ArrayList<>();
            for (Item item : list){
                List<MaterialStock> materialStockList = materialStockMapper.selectByItemId(item.getItemId());
                ItemMaterialDetailVo itemMaterialDetailVo = new ItemMaterialDetailVo(item,materialStockList);
                itemMaterialDetailVos.add(itemMaterialDetailVo);
            }
            PageInfo<ItemMaterialDetailVo> pageInfo = new PageInfo(list);
            pageInfo.setList(itemMaterialDetailVos);
            return ServerResponse.createBySuccess(pageInfo);
        }
        return ServerResponse.createByErrorMessage("没有任何项目");
    }

}
