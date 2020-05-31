package com.hhu.cshelper.presenter;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhu.cshelper.common.Constants;
import com.hhu.cshelper.model.bean.remote.User;
import com.hhu.cshelper.net.OkHttpUtils;
import com.hhu.cshelper.presenter.contract.UserContract;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/** 用户操作控制类
 * @name  UserPresenter
 * @description 用户操作控制类
 * @author  nlby
 * @date  2020/5/6
 */
public class UserPresenter extends RxPresenter<UserContract.View> implements UserContract.Presenter{

    @Override
    public void login(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        OkHttpUtils.getInstance().get(Constants.URL_USER_LOGIN, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                if (s.contains("400") || s.contains("500") || s.contains("404")) {
                    mView.onFailure(new Throwable("服务器响应错误，请重试，该错误可能是服务器过久未访问所致，重试应可解决问题"));
                    return;
                }
                Type type = new TypeToken<User>(){}.getType();
                User user = new Gson().fromJson(s, type);
                if (user.getStatus() == 200) {
                    mView.onFailure(new Throwable(user.getMessage()));
                } else {
                    mView.loginSuccess(user);
                }
            }
        });
    }

    @Override
    public void signup(String username, String password, String phone, String mail) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("phone", phone);
        params.put("mail", mail);
        OkHttpUtils.getInstance().get(Constants.URL_USER_SIGN, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                if (s.contains("400") || s.contains("500") || s.contains("404")) {
                    mView.onFailure(new Throwable("服务器响应错误，请重试，该错误可能是服务器过久未访问所致，重试应可解决问题"));
                    return;
                }
                Type type = new TypeToken<User>(){}.getType();
                User user = new Gson().fromJson(s, type);
                if (user.getStatus() == 200) {
                    mView.onFailure(new Throwable(user.getMessage()));
                } else {
                    mView.loginSuccess(user);
                }
            }
        });
    }
}
