package com.hhu.cshelper.presenter;

import com.hhu.cshelper.model.bean.local.History;
import com.hhu.cshelper.model.repository.LocalRepository;
import com.hhu.cshelper.presenter.contract.HistoryContract;

import java.util.List;

/** 历史记录操作控制类
 * @name  HistoryPresenter
 * @description  历史记录操作控制类
 * @author  nlby
 * @date  2020/4/29
 */
public class HistoryPresenter extends RxPresenter<HistoryContract.View> implements HistoryContract.Presenter{
    @Override
    public Long saveHistory(History history) {
        return LocalRepository.getInstance().saveHistory(history);
    }

    @Override
    public List<String> getDatesOfHistory() {
        return LocalRepository.getInstance().getDatesOfHistory();
    }

    @Override
    public List<History> getHistoryByDate(String date) {
        return LocalRepository.getInstance().getHistoryByDate(date);
    }
}