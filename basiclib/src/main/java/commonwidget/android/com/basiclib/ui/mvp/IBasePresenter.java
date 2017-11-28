package commonwidget.android.com.basiclib.ui.mvp;

public interface IBasePresenter<T> {

    void attachView(T view);

    void detachView();

}
