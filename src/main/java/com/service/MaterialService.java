package com.service;

import com.common.ServerResponse;
import com.pojo.MaterialBuyInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by upupgogogo on 2018/8/6.下午4:04
 */
public interface MaterialService {

    ServerResponse buyMaterial(MaterialBuyInfo materialBuyInfo, MultipartFile file, String path);
}
