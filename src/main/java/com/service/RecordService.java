package com.service;

import com.common.ServerResponse;
import com.pojo.Record;
import com.pojo.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by upupgogogo on 2018/9/4.下午1:47
 */
public interface RecordService {

    ServerResponse addRecord(User user, Record record, String path, MultipartFile[] files);
}
