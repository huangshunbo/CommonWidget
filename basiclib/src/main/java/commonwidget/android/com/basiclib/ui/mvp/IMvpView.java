package commonwidget.android.com.basiclib.ui.mvp;

public interface IMvpView {
    void showLoading(String tips);

    void dismissLoading();

    void showToast(String msg);
}
