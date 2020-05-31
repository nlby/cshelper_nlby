package com.hhu.cshelper.presenter.contract;



import com.hhu.cshelper.model.bean.local.History;

import java.util.List;

public interface HistoryContract extends BaseContract{
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 创建历史记录
         */
        Long saveHistory(History history);

        /**
         * 获取所有日期
         */
        List<String> getDatesOfHistory();

        /**
         * 根据日期获取历史记录
         */
        List<History> getHistoryByDate(String date);
    }
}
