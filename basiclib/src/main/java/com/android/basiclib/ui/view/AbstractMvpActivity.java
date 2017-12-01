package com.android.basiclib.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.basiclib.log.LogUtil;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.basiclib.ui.mvp.IBaseView;

public abstract class AbstractMvpActivity<T extends IBasePresenter> extends BaseActivity implements IBaseView{

    protected T mPresenter;
    protected abstract T createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            LogUtil.d("onCreate Presenter attachView");
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter=null;
        }
    }


    @Override
    public void showLoading(String tips) {
        showDataLoading();
    }

    @Override
    public void dismissLoading() {
        dissMissDataLoading();
    }

    @Override
    public void showRequestFailure(String taskId) {
        showDataError();
    }

    @Override
    public void showRequestNotData(String taskId) {
        showDataEmpty();
    }

    @Override
    public void showRequestNotNet(String taskId) {
        showDataNotNetwork();
    }

    @Override
    public void showSuccess(String taskId) {
        showDataSuccess();
    }
}
