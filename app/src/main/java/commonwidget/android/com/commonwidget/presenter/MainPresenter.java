package commonwidget.android.com.commonwidget.presenter;

import commonwidget.android.com.basiclib.log.LogUtil;
import commonwidget.android.com.basiclib.ui.mvp.BasePresenter;
import commonwidget.android.com.commonwidget.contract.IMainActivityContract;

public class MainPresenter extends BasePresenter<IMainActivityContract.IView>
        implements IMainActivityContract.IPresenter {

    @Override
    public void requestData() {
        if(isViewAttached()){
            LogUtil.d("发起网络请求");
            getView().showData();
        }
    }
}
