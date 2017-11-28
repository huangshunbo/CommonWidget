package commonwidget.android.com.basiclib;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.tencent.bugly.crashreport.CrashReport;

import commonwidget.android.com.basiclib.log.LogUtil;

public class MApplication extends Application{

    public static MApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        LogUtil.d("MApplication oncreate");

        AppConfig.init();
        initSdk();
    }

    private void initSdk() {
        //bugly
        CrashReport.initCrashReport(getApplicationContext());
    }


}
