package com.android.commonwidget.presenter;

import com.android.basiclib.log.LogUtil;
import com.android.basiclib.net.callback.DefaultBeanCallBack;
import com.android.basiclib.ui.mvp.BasePresenter;
import com.android.commonwidget.bean.User;
import com.android.commonwidget.contract.IMainActivityContract;
import com.android.commonwidget.repository.MainRepository;

public class MainPresenter extends BasePresenter<IMainActivityContract.IView>
        implements IMainActivityContract.IPresenter {

    @Override
    public void requestData() {
        if(isViewAttached()){
            MainRepository.requestTest(new DefaultBeanCallBack<User>() {

                @Override
                public void onSuccess(User o) {
                    LogUtil.d("requestData onSuccess");
                    LogUtil.d(o);
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
            getView().showData();
        }
    }
}
