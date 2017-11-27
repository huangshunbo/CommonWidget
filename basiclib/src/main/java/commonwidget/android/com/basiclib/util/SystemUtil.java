package commonwidget.android.com.basiclib.util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import commonwidget.android.com.basiclib.MApplication;

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
}
