package com.n.service;

import com.n.domain.BookMark;

import java.util.List;

public interface IBookMarkService {

    // 同步移动端书签表中的书签到web端书签表
    public void syncBookMark(String userId) throws Exception;

    // 查询某用户所有书签信息 分页查询
    public List<BookMark> findAllByUserId(String userId, int page, int size) throws Exception;
}
