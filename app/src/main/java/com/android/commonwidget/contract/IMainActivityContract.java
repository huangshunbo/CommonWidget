package com.android.commonwidget.contract;

import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.basiclib.ui.mvp.IBaseView;

public interface IMainActivityContract {

    interface IView extends IBaseView {
        void showData();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void requestData();
    }
}
