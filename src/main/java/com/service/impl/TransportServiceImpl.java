package com.service.impl;

import com.common.ServerResponse;
import com.dao.TransportMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.OfferMaterial;
import com.pojo.Transport;
import com.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/11/19.下午10:00
 */
@Service("transportService")
public class TransportServiceImpl implements TransportService {

    @Autowired
    private TransportMapper transportMapper;
    @Override
    public ServerResponse add(Transport transport) {
        if (transport.getTransportName() == null && transport.getTransportNumber() == null && transport.getTransportPhone() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");

        int count = transportMapper.selectCountByName(transport.getTransportName());
        if (count > 0)
            return ServerResponse.createByErrorMessage("运输单位名已存在");
        int row = transportMapper.insert(transport);
        if (row > 0)
            return ServerResponse.createBySuccess("添加成功");
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse delete(Integer transportId) {
        if (transportId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        int row = transportMapper.deleteByPrimaryKey(transportId);
        if (row > 0){
            return ServerResponse.createBySuccess("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("transport_id desc");
        List<Transport> list = transportMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse update(Transport transport) {
        if (transport.getTransportId() == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        if (transport.getTransportName() != null){
            int count = transportMapper.selectCountByNameAndId(transport.getTransportName(),transport.getTransportId());
            if (count > 0)
                return ServerResponse.createByErrorMessage("运输单位名已存在");
        }

        int row = transportMapper.updateByPrimaryKeySelective(transport);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<PageInfo<Transport>> getTransportByName(int pageSize, int pageNum, String transportName) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("transport_id desc");
        if (transportName == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        transportName = "%" + transportName + "%";
        List<Transport> list = transportMapper.selectByTransportName(transportName);
        PageInfo<Transport> pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<Transport> getTransportById(Integer transportId) {
        if (transportId == null)
            return ServerResponse.createByErrorMessage("参数为空");
        Transport transport = transportMapper.selectByPrimaryKey(transportId);
        return ServerResponse.createBySuccess(transport);
    }
}
