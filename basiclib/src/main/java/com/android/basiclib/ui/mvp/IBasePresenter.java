package com.android.basiclib.ui.mvp;

public interface IBasePresenter<T> {

    void attachView(T view);

    void detachView();

}
