package com.hhu.cshelper.presenter.contract;



import com.hhu.cshelper.model.bean.local.BookMark;

import java.util.HashSet;
import java.util.List;

public interface BookMarkContract extends BaseContract{
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View>{

        /**
         * 创建书签
         */
        Long saveBookMark(BookMark bookMark);

        /**
         * 获取所有书签类型
         */
        HashSet<String> getTypesOfBookMark();

        /**
         * 根据类型获取书签
         */
        List<BookMark> getBookMarkByType(String type);


    }


}
