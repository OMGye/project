package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.OfferMaterial;
import com.pojo.Transport;

/**
 * Created by upupgogogo on 2018/11/19.下午9:58
 */
public interface TransportService {

    ServerResponse add(Transport transport);

    ServerResponse delete(Integer transportId);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);

    ServerResponse update(Transport transport);

    ServerResponse<PageInfo<Transport>> getTransportByName(int pageSize, int pageNum,String transportName);

    ServerResponse<Transport> getTransportById(Integer offererId);
}
