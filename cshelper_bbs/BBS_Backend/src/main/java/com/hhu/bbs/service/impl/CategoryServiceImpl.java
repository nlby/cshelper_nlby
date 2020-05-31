package com.hhu.bbs.service.impl;

import com.hhu.bbs.util.format.FormatResult;
import com.hhu.bbs.util.format.FormatResultGenerator;
import com.hhu.bbs.dao.CategoryDao;
import com.hhu.bbs.entity.Category;
import com.hhu.bbs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *  分类 Service
 * @name  CategoryServiceImpl
 * @author  nlby
 * @date  2020/5/14
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * 查询所有分类列表
     * @return
     */
    @Override
    public FormatResult<List<Category>> findAll() {
        List<Category> categories = categoryDao.findAll();
        return FormatResultGenerator.genSuccessResult(categories);
    }

    /**
     * 查询某版块下的所有category
     * @return
     */
    @Override
    public FormatResult<List<Category>> findAllByBlockId(BigInteger id) {
        List<Category> categories = categoryDao.findAllByBlockId(id);
        return FormatResultGenerator.genSuccessResult(categories);
    }

    /**
     * 查询某个分类信息
     * @param id
     * @return
     */
    @Override
    public FormatResult<Map<String, Object>> getCategoryById(BigInteger id) {
        Map<String, Object> result = categoryDao.getCategoryById(id);
        return FormatResultGenerator.genSuccessResult(result);
    }

    /**
     * 添加分类
     * @param id 版块对应block id
     * @param name
     * @param description
     * @return
     */
    @Override
    public FormatResult<Object> addCategory(BigInteger id, String name, String description) {
        int i = categoryDao.addCategory(id, name, description);
        return FormatResultGenerator.genSuccessResult(i);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @Override
    public FormatResult<Object> delCategory(BigInteger id) {
        int i = categoryDao.delCategory(id);
        return FormatResultGenerator.genSuccessResult(i);
    }
}
