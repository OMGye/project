package com.service;

import com.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import com.pojo.Category;

/**
 * Created by upupgogogo on 2018/8/6.下午12:47
 */
public interface CategoryService {

    ServerResponse add(Category category);

    ServerResponse delete(Integer categoryId);

    ServerResponse<PageInfo> list(int pageSize, int pageNum);

    ServerResponse update(Category category);
}
