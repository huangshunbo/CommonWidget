package com.android.basiclib;

import android.app.Application;


import com.alibaba.android.arouter.launcher.ARouter;
import com.android.basiclib.log.LogUtil;
import com.networkbench.agent.impl.NBSAppAgent;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.greendao.database.Database;

public class MApplication extends Application{

    public static MApplication application;
//    private DaoSession daoSession;
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
        //性能管理 听云SDK
        NBSAppAgent.setLicenseKey("8ee9ab73668a4392b5bc5d80c4971a76").startInApplication(this.getApplicationContext());
        ARouter.init(this);

//        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
//        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
    }

//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
}
