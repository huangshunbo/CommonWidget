package com.android.basiclib.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.basiclib.MApplication;
import com.android.basiclib.ui.util.SystemBarTintManager;

public class SystemUtil {

    public static String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = MApplication.application.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(MApplication.application.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }
    //获取AndroidManifest的META_DATA
    public static String getMetaData(String meta) throws PackageManager.NameNotFoundException {
        return MApplication.application.getPackageManager()
                .getApplicationInfo(MApplication.application.getPackageName(), PackageManager.GET_META_DATA).metaData
                .getString(meta);
    }

    public static void setStatusBarColor(int colorID,Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColorLOLLIPOP(colorID,activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true,activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(false);
            tintManager.setStatusBarTintColor(colorID);
        }
    }
    @TargetApi(21)
    private static void setStatusBarColorLOLLIPOP(int colorID,Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(colorID);
        window.setNavigationBarColor(ContextCompat.getColor(activity, commonwidget.android.com.basiclib.R.color.black));
    }
    @TargetApi(19)
    private static void setTranslucentStatus(boolean on,Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) winParams.flags |= bits;
        else winParams.flags &= ~bits;
        window.setAttributes(winParams);
    }
}
