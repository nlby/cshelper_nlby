package com.hhu.cshelper.activity.system;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hhu.cshelper.R;
import com.hhu.cshelper.activity.BaseMVPActivity;
import com.hhu.cshelper.activity.web.FreeWebActivity;
import com.hhu.cshelper.model.bean.remote.User;
import com.hhu.cshelper.presenter.UserPresenter;
import com.hhu.cshelper.presenter.contract.UserContract;
import com.hhu.cshelper.utils.ProgressUtils;
import com.hhu.cshelper.utils.SnackbarUtils;
import com.hhu.cshelper.utils.StringUtils;
import com.hhu.cshelper.widget.OwlView;

/** 登录/注册页
 * @name  LoginSignActivity
 * @description  登录/注册页
 * @author  nlby
 * @date  2020/4/29
 */
public class LoginSignActivity extends BaseMVPActivity<UserContract.Presenter>
        implements UserContract.View, View.OnFocusChangeListener, View.OnClickListener {

    private OwlView owlView;
    private EditText emailET;
    private EditText phoneET;
    private EditText usernameET;
    private EditText passwordET;
    private EditText rpasswordET;
    private TextView signTV;
    private TextView forgetTV;
    private TextView loginTV;

    private Context context;
    private boolean isLogin = true;  // 是否是登录操作

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginsign);
        context = this;
        attachView(bindPresenter());
        initWidget();

        SharedPreferences sharedPreferences = getSharedPreferences("NBPref", MODE_PRIVATE);
        if (!sharedPreferences.getString("username", "").equals("")) {
            Intent intent = new Intent(context, FreeWebActivity.class);
            startActivity(intent);
            finish();
        }
    }


    /**
     *  初始化控件及事件监听
     */
    protected void initWidget() {
        owlView = findViewById(R.id.land_owl_view);
        emailET = findViewById(R.id.login_et_email);
        phoneET = findViewById(R.id.login_et_phone);
        usernameET = findViewById(R.id.login_et_username);
        passwordET = findViewById(R.id.login_et_password);
        rpasswordET = findViewById(R.id.login_et_rpassword);
        signTV = findViewById(R.id.btn_sign);
        forgetTV = findViewById(R.id.btn_forget);
        loginTV = findViewById(R.id.btn_login);

        passwordET.setOnFocusChangeListener(this);
        rpasswordET.setOnFocusChangeListener(this);
        signTV.setOnClickListener(this);
        forgetTV.setOnClickListener(this);
        loginTV.setOnClickListener(this);
    }


    /**
     *  事件监听：登录 注册 忘记密码
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:  //button
                if (isLogin) {
                    login();  //登陆
                } else {
                    sign();  //注册
                }
                break;
            case R.id.btn_sign:  //sign
                if (isLogin) {
                    //置换注册界面
                    signTV.setText("Login");
                    loginTV.setText("Sign Up");
                    rpasswordET.setVisibility(View.VISIBLE);
                    emailET.setVisibility(View.VISIBLE);
                    phoneET.setVisibility(View.VISIBLE);
                } else {
                    //置换登陆界面
                    signTV.setText("Sign Up");
                    loginTV.setText("Login");
                    rpasswordET.setVisibility(View.GONE);
                    emailET.setVisibility(View.GONE);
                    phoneET.setVisibility(View.GONE);
                }
                isLogin = !isLogin;
                break;
            case R.id.btn_forget:  //忘记密码
                break;
            default:
                break;
        }
    }

    /**
     *  焦点是否在密码框上
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            owlView.open();
        } else {
            owlView.close();
        }
    }

    /**
     * 执行登录动作
     */
    public void login() {
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        if (username.length() == 0 || password.length() == 0) {
            SnackbarUtils.show(context, "用户名或密码不能为空");
            return;
        }
        ProgressUtils.show(this, "正在登陆...");
        mPresenter.login(username, password);
    }

    /**
     *  执行注册动作
     */
    public void sign() {
        String email = emailET.getText().toString();
        String phone = phoneET.getText().toString();
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        String rpassword = rpasswordET.getText().toString();
        if (email.length() == 0 || username.length() == 0 || password.length() == 0 || rpassword.length() == 0 || phone.length() == 0) {
            SnackbarUtils.show(context, "请填写必要信息");
            return;
        }
        if (!StringUtils.checkEmail(email)) {
            SnackbarUtils.show(context, "请输入正确的邮箱格式");
            return;
        }
        if (!password.equals(rpassword)) {
            SnackbarUtils.show(context, "两次密码不一致");
            return;
        }
        ProgressUtils.show(this, "正在注册...");
        mPresenter.signup(username,password,phone, email);

    }

    /**
     *  登录/注册成功的回调
     */
    @Override
    public void loginSuccess(User user) {
        ProgressUtils.dismiss();
        final User u = user;
        if (isLogin) {
            LoginSignActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "用户" + u.getUsername() + "登录成功", Toast.LENGTH_LONG).show();
                    //自写逻辑  将用户信息 用户名 密码 id 存储在本地 跳转至首页
                    SharedPreferences sharedPreferences = getSharedPreferences("NBPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String username = usernameET.getText().toString();
                    String password = passwordET.getText().toString();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putString("userId", u.getId());
                    editor.putString("mail", u.getMail());
                    editor.putString("phone", u.getPhone());
                    editor.commit();
                    Intent intent = new Intent(context, FreeWebActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else {
            //自写逻辑  将用户信息 用户名 密码 id 存储在本地 跳转至首页
            SharedPreferences sharedPreferences = getSharedPreferences("NBPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putString("userId", user.getId());
            editor.putString("mail", u.getMail());
            editor.putString("phone", u.getPhone());
            editor.commit();
            //.........
//            SnackbarUtils.show(context, "注册成功");
            SnackbarUtils.show(context, u.getMessage());

        }
        Log.i("login", user.toString());
    }

    @Override
    protected UserContract.Presenter bindPresenter() {
        return new UserPresenter();
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void onFailure(Throwable e) {
        ProgressUtils.dismiss();
        if (e.getMessage() != null && e.getMessage().length() > 0) {
            SnackbarUtils.show(context,e.getMessage());
        } else {
            SnackbarUtils.show(context,"响应失败，请重试");
        }
        Log.e("login","响应失败，请重试");
    }
}
