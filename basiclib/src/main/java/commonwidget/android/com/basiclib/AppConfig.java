package commonwidget.android.com.basiclib;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class AppConfig {

    public static boolean APP_DEBUG = true;

    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info= context.getApplicationInfo();
            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {

        }
        return false;
    }

    public static void init() {
        APP_DEBUG = isApkDebugable(MApplication.application);
    }
}
