package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.Record;
import com.pojo.User;
import com.vo.RecordAmountVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by upupgogogo on 2018/9/4.下午1:47
 */
public interface RecordService {

    ServerResponse addRecord(User user, Record record, String path, MultipartFile[] files);

    ServerResponse addRecordImg(User user, Integer recordId, String path, MultipartFile file);

    ServerResponse deleteRecordImg(User user, Integer recordId, String fileName, String name);

    ServerResponse<PageInfo> list(User user, Integer state, Integer type, int pageSize, int pageNum);

   ServerResponse update(User user, Record record);

    ServerResponse managerCheck(User user, Integer recordId);

    ServerResponse<PageInfo> AllList(Integer itemId, Integer state, Integer type, int pageSize, int pageNum);

    ServerResponse financialCheck(Integer recordId);

    ServerResponse getByRecordId(User user,Integer recordId);

    ServerResponse refuseRecord(User user, Integer recordId, String recordRefuse);

    ServerResponse<PageInfo> listByDec(User user, Integer state, String recordDec , int pageSize, int pageNum);

    ServerResponse deleteByRecordId(User user,Integer recordId);

    ServerResponse<PageInfo> listByOfferId(User user, Integer state, Integer offerId , int pageSize, int pageNum);

   ServerResponse<RecordAmountVo> getRecordAmount(Integer itemId, Integer offerId);

}
