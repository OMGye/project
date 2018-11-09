package com.service.impl;

import com.common.ServerResponse;
import com.dao.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.Category;
import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by upupgogogo on 2018/8/6.下午12:47
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse add(Category category) {
        if (category.getCategoryName() == null)
            return ServerResponse.createByErrorMessage("名字不能为空");
        List<Category> dbCategoryList = categoryMapper.selectCountByName(category.getCategoryName());
        if (dbCategoryList.size() >= 1){
            for (Category dbCategory : dbCategoryList)
                if (dbCategory.getSpecifications().equals(category.getSpecifications())) return ServerResponse.createByErrorMessage("分类名下该规格存在");
        }
        int row = categoryMapper.insert(category);
        if (row > 0)
            return ServerResponse.createBySuccess("添加成功");
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse delete(Integer categoryId) {
        if (categoryId == null)
            return ServerResponse.createByErrorMessage("参数错误");
        int row = categoryMapper.deleteByPrimaryKey(categoryId);
        if (row > 0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse<PageInfo> list(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("category_id desc");
        List<Category> list = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse update(Category category) {
        if (category.getCategoryId() == null)
            return ServerResponse.createByErrorMessage("参数错误");
        if (category.getCategoryName() != null){
            List<Category> dbCategoryList = categoryMapper.selectCountByName(category.getCategoryName());
            if (dbCategoryList.size() >= 1){
                for (Category dbCategory : dbCategoryList)
                    if (dbCategory.getSpecifications().equals(category.getSpecifications())) return ServerResponse.createByErrorMessage("分类名下该规格存在");
            }
        }
        int row = categoryMapper.updateByPrimaryKeySelective(category);
        if (row > 0)
            return ServerResponse.createBySuccess("修改成功");
        return ServerResponse.createByErrorMessage("修改失败");
    }

    @Override
    public ServerResponse<PageInfo<Category>> getCategoryByName(int pageSize, int pageNum,String categoryName) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("category_id desc");
        if (categoryName == null)
            return ServerResponse.createByErrorMessage("参数不能为空");
        categoryName = "%" + categoryName + "%";
        List<Category> list = categoryMapper.selectByCategoryName(categoryName);
        PageInfo<Category> pageInfo = new PageInfo(list);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
