package com.hhu.cshelper.presenter.contract;


import com.hhu.cshelper.model.bean.remote.User;

public interface UserContract extends BaseContract {

    interface View extends BaseView {
        void loginSuccess(User user);
    }

    interface Presenter extends BasePresenter<View>{
        /**
         * 用户登陆
         */
        void login(String username, String password);

        /**
         * 用户注册
         */
        void signup(String username, String password, String phone, String mail);

    }
}
