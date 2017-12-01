package com.android.basiclib.ui.mvp;

public interface IBaseView extends IMvpView {
    void showRequestFailure(String taskId);

    void showRequestNotData(String taskId);

    void showRequestNotNet(String taskId);

    void showSuccess(String taskId);
}
