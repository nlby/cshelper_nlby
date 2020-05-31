package com.n.service.impl;

import com.github.pagehelper.PageHelper;
import com.n.dao.IBookMarkDao;
import com.n.domain.BookMark;
import com.n.service.IBookMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class BookMarkServiceImpl implements IBookMarkService {
    @Autowired
    private IBookMarkDao bookMarkDao;
    @Override
    public void syncBookMark(String userId) throws Exception {
        List<BookMark>  bookMarkList = bookMarkDao.findAllByUserIdFromMobile(userId);
        for (int i = 1; i <= bookMarkList.size(); i++) {
            for (BookMark bookMark: bookMarkList) {
                if (Integer.parseInt(bookMark.getRid()) == i) {
                    bookMark.setRownum(i);
                    bookMarkDao.saveBookMark(bookMark);
                }
            }
        }
    }

    @Override
    public List<BookMark> findAllByUserId(String userId, int page, int size) throws Exception {
        syncBookMark(userId);
        PageHelper.startPage(page, size);
        return bookMarkDao.findAll(userId);
    }
}
