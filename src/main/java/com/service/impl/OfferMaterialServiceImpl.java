package com.service.impl;

import com.common.ServerResponse;
import com.dao.CategoryMapper;
import com.dao.OfferMaterialMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Category;
import com.pojo.OfferMaterial;
import com.service.CategoryService;
import com.service.OfferMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/8/6.下午12:47
 */
@Service("offerMaterialService")
public class OfferMaterialServiceImpl implements OfferMaterialService{

    @Autowired
    private OfferMaterialMapper offerMaterialMapper;

    @Override
    public ServerResponse add(OfferMaterial offerMaterial) {
        if (offerMaterial.getAddress() == null)
            return ServerResponse.createByErrorMessage("公司地址不能为空");
        if (offerMaterial.getOfferCompany() == null)
            return ServerResponse.createByErrorMessage("公司名不能为空");
        int count = offerMaterialMapper.selectCountByName(offerMaterial.getOfferCompany());
        if (count > 0)
            return ServerResponse.createByErrorMessage("公司名已存在");
        int row = offerMaterialMapper.insert(offerMaterial);
        if (row > 0)
            return ServerResponse.createBySuccess("添加成功");
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse delete(Integer offerId) {
        if (offerId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        int row = offerMaterialMapper.deleteByPrimaryKey(offerId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<OfferMaterial> list = offerMaterialMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse update(OfferMaterial offerMaterial) {
        if (offerMaterial.getOfferId() == null)
            return ServerResponse.createByErrorMessage("参数错误");
        if (offerMaterial.getOfferCompany() != null){
            int count = offerMaterialMapper.selectCountByNameAndId(offerMaterial.getOfferCompany(),offerMaterial.getOfferId());
            if (count > 0)
                return ServerResponse.createByErrorMessage("公司名已存在");
        }

        int row = offerMaterialMapper.updateByPrimaryKeySelective(offerMaterial);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }
}
