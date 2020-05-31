package com.hhu.cshelper.presenter;

import com.hhu.cshelper.model.bean.local.BookMark;
import com.hhu.cshelper.model.repository.LocalRepository;
import com.hhu.cshelper.presenter.contract.BookMarkContract;

import java.util.HashSet;
import java.util.List;

/** 书签操作控制类
 * @name  BookMarkPresenter
 * @description  书签操作控制类
 * @author  nlby
 * @date  2020/4/29
 */
public class BookMarkPresenter extends RxPresenter<BookMarkContract.View> implements BookMarkContract.Presenter {
    @Override
    public Long saveBookMark(BookMark bookMark) {
        return LocalRepository.getInstance().saveBookMark(bookMark);
    }

    @Override
    public HashSet<String> getTypesOfBookMark() {
        return LocalRepository.getInstance().getTypesOfBookMark();
    }

    @Override
    public List<BookMark> getBookMarkByType(String type) {
        return LocalRepository.getInstance().getBookMarkByType(type);
    }
}