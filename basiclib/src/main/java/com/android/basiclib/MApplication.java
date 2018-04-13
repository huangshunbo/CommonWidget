package com.android.basiclib;

import android.app.Application;


import com.alibaba.android.arouter.launcher.ARouter;
import com.android.basiclib.log.LogUtil;
import com.baidu.mobstat.StatService;
import com.networkbench.agent.impl.NBSAppAgent;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

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
//        路由管理ARouter
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
//        百度统计
        // @param autoTrace：如果设置为true，打开⾃动埋点；反之关闭
        // @param autoTrackWebview：false
        StatService.autoTrace(this, true,false);
//        统计WebView
//        StatService.trackWebView(this,webview,webviewClient);
        StatService.onEvent(this,"10086","测试Application启动");
        StatService.onEvent(this,"10086","测试Application启动111");
        StatService.onEvent(this,"10086","测试");
//        友盟分享
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
        //配置
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }


}
