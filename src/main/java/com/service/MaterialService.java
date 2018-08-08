package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.MaterialBuyInfo;
import com.pojo.MaterialStock;
import com.pojo.MaterialUseInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by upupgogogo on 2018/8/6.下午4:04
 */
public interface MaterialService {

    ServerResponse buyMaterial(MaterialBuyInfo materialBuyInfo, MultipartFile file, String path);

    ServerResponse<List<MaterialStock>> getMaterialStockByItemId(Integer itemId);

    ServerResponse useMaterial(MaterialUseInfo materialUseInfo);

    ServerResponse updateState(Integer materialUserId, Integer materialInfoId);

    ServerResponse<List> getUncheckList(Integer itemId);

    ServerResponse<List> getUncheckUseList(Integer itemId);

    ServerResponse getMaterialDetail(Integer materialUserId, Integer materialInfoId);
}
