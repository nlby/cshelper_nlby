package com.hhu.cshelper.presenter;

import android.database.Cursor;

import com.hhu.cshelper.model.bean.local.Code;
import com.hhu.cshelper.model.repository.LocalRepository;
import com.hhu.cshelper.presenter.contract.CodeContract;

/** 代码操作控制类
 * @name  CodePresenter
 * @description  代码操作控制类
 * @author  nlby
 * @date  2020/4/29
 */
public class CodePresenter extends RxPresenter<CodeContract.View> implements CodeContract.Presenter{

    @Override
    public Long saveCode(Code code) {
        return LocalRepository.getInstance().saveCode(code);
    }

    @Override
    public Code getCodeById(long id) {
        return LocalRepository.getInstance().getCodeById(id);
    }

    @Override
    public Cursor getAllCodes() {
        return LocalRepository.getInstance().getAllCodes();
    }

    @Override
    public void updateCode(Code code) {
        LocalRepository.getInstance().updateCode(code);
        mView.onSuccess();
    }

    @Override
    public void deleteCodeById(long id) {
        LocalRepository.getInstance().deleteCodeById(id);
        mView.onSuccess();
    }

    @Override
    public void createCode(String title, String body, String rid, String userId) {
        LocalRepository.getInstance().createCode(title, body, rid, userId);
    }

    @Override
    public void updateCode(long rowId, String title, String body) {
        LocalRepository.getInstance().updateCode(rowId, title, body);
    }
}
