package com.n.service.impl;

import com.github.pagehelper.PageHelper;
import com.n.dao.IBlogDao;
import com.n.domain.Blog;
import com.n.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements IBlogService {

    @Autowired
    private IBlogDao blogDao;
    @Override
    public void save(Blog blog) throws Exception {
        int rownum = blogDao.getCount(blog.getUserId());
        blog.setRownum(++rownum);
        blogDao.saveBlog(blog);
    }

    @Override
    public List<Blog> findAllByUserId(String userId, int page, int size) throws Exception {
        PageHelper.startPage(page, size);
        return blogDao.findAll(userId);
    }

    @Override
    public Blog findById(String id) throws Exception {
        return blogDao.findById(id);
    }
}
