package com.hhu.cshelper.presenter;

import com.hhu.cshelper.presenter.contract.BaseContract;

/** 数据操作控制基类
 * @name  RxPresenter
 * @description  数据操作控制基类
 * @author  nlby
 * @date  2020/4/29
 */
public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
