package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.OfferMaterial;

import java.util.List;

/**
 * Created by upupgogogo on 2018/8/6.下午12:47
 */
public interface OfferMaterialService {

    ServerResponse add(OfferMaterial offerMaterial);

    ServerResponse delete(Integer offerId);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);

    ServerResponse update(OfferMaterial offerMaterial);

    ServerResponse<PageInfo<OfferMaterial>> getOffererByName(int pageSize, int pageNum, String offererName);

    ServerResponse<OfferMaterial> getOffererById(Integer offererId);
}
