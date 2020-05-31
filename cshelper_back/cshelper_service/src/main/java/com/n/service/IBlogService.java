package com.n.service;

import com.n.domain.Blog;

import java.util.List;

public interface IBlogService {

    // 添加blog
    public void save(Blog blog) throws Exception;

    // 查询某用户所有blog信息 分页查询
    public List<Blog> findAllByUserId(String userId, int page, int size) throws Exception;

    // 根据id查询blog
    public Blog findById(String id) throws Exception;
}
