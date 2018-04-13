package com.android.basiclib.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.basiclib.log.LogUtil;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.basiclib.ui.mvp.IBaseView;
import com.android.basiclib.ui.view.BaseActivity;

public abstract class AbstractMvpFragment<T extends IBasePresenter> extends BaseFragment implements IBaseView {

    protected T mPresenter;

    protected abstract T createPresenter();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            LogUtil.d("onCreate Presenter attachView");
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void showLoading(String tips) {
        if(mActivity instanceof BaseActivity){
            ((BaseActivity)mActivity).showDataLoading();
        }
    }

    @Override
    public void dismissLoading() {
        if(mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).dissMissDataLoading();
        }
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showRequestFailure(String taskId) {
        if(mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showDataError();
        }
    }

    @Override
    public void showRequestNotData(String taskId) {
        if(mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showDataEmpty();
        }
    }

    @Override
    public void showRequestNotNet(String taskId) {
        if(mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showDataNotNetwork();
        }
    }

    @Override
    public void showSuccess(String taskId) {
        if(mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showDataSuccess();
        }
    }
}