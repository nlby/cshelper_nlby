package com.hhu.bbs.service;

import com.hhu.bbs.entity.Category;
import com.hhu.bbs.util.format.FormatResult;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface CategoryService {
    /**
     * 查询所有分类列表
     * @return
     */
    FormatResult<List<Category>> findAll();

    /**
     * 查询某版块下的所有category
     * @return
     */
    FormatResult<List<Category>> findAllByBlockId(BigInteger id);

    /**
     * 查询某个分类信息
     * @param id
     * @return
     */
    FormatResult<Map<String, Object>> getCategoryById(BigInteger id);

    /**
     * 添加分类
     * @param id 版块对应block id
     * @param name
     * @param description
     * @return
     */
    FormatResult<Object> addCategory(BigInteger id, String name, String description);

    /**
     * 删除分类
     * @param id
     * @return
     */
    FormatResult<Object> delCategory(BigInteger id);
}
