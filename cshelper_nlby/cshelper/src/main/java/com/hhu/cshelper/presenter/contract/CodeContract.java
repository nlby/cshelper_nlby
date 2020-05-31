package com.hhu.cshelper.presenter.contract;

import android.database.Cursor;

import com.hhu.cshelper.model.bean.local.Code;


public interface CodeContract extends BaseContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View>{

        /**
         * 创建笔记
         */
        Long saveCode(Code code);

        /**
         * 根据id获取笔记
         */
        Code getCodeById(long id);

        /**
         * 获取所有笔记
         */
        Cursor getAllCodes();

        /**
         * 更新笔记
         */
        void updateCode(Code code);

        /**
         * 删除笔记
         */
        void deleteCodeById(long id);

        /**
         * 创建笔记 按需封装
         */
        void createCode(String title, String body, String rid, String userId);

        /**
         * 更新笔记 按需封装
         */
        void updateCode(long rowId, String title, String body);
    }
}
