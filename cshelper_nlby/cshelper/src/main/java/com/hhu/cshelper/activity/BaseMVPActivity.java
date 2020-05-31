package com.hhu.cshelper.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.hhu.cshelper.presenter.contract.BaseContract;

/** MVP模式中View的基类
 * @name  BaseMVPActivity
 * @description  MVP模式中View的基类
 * @author  nlby
 * @date  2020/4/29
 */
public abstract class BaseMVPActivity<T extends BaseContract.BasePresenter> extends AppCompatActivity {

    protected T mPresenter;

    protected abstract T bindPresenter();

    protected void attachView(T presenter){
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }


}
