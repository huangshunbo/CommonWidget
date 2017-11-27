package commonwidget.android.com.basiclib;

import android.app.Application;

import commonwidget.android.com.basiclib.log.LogUtil;

public class MApplication extends Application{

    public static MApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        LogUtil.d("MApplication oncreate");

    }
}
