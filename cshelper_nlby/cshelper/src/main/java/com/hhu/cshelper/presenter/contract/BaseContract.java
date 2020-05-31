package com.hhu.cshelper.presenter.contract;


public interface BaseContract {

    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        void onSuccess();

        void onFailure(Throwable e);
    }
}
