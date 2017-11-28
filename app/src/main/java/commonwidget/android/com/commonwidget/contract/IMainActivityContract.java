package commonwidget.android.com.commonwidget.contract;

import commonwidget.android.com.basiclib.ui.mvp.IBasePresenter;
import commonwidget.android.com.basiclib.ui.mvp.IBaseView;

public interface IMainActivityContract {

    interface IView extends IBaseView {
        void showData();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void requestData();
    }
}
