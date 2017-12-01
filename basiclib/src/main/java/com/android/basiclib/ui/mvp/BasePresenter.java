package com.android.basiclib.ui.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class BasePresenter<T> implements IBasePresenter<T> {

    /***todo描述这个字段的含义***/
    protected Reference<T> mViewRef;//View接口类型弱引用

    @Override
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view); //建立关联
    }

    protected T getView() {
        return mViewRef.get();//获取View
    }

    public boolean isViewAttached() {//判断是否与View建立了关联
        return mViewRef != null && mViewRef.get() != null;
    }

    @Override
    public void detachView() {//解除关联
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

}
