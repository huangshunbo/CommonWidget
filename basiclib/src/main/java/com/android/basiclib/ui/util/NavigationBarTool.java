package com.android.basiclib.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.reflect.Method;

public class NavigationBarTool {
    /**
     * <br> Description:  下面方法可判断手机是否有虚拟按键功能：
     * <br> Date:        2017/8/16 16:56
     */
    public static boolean hasNavigationBar(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        float density = dm.density;

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(realDisplayMetrics);
        } else {
            Class c;
            try {
                c = Class.forName("android.view.Display");
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, realDisplayMetrics);
            } catch (Exception e) {
                realDisplayMetrics.setToDefaults();
                e.printStackTrace();
            }
        }

        int screenRealHeight = realDisplayMetrics.heightPixels;
        int screenRealWidth = realDisplayMetrics.widthPixels;

        float diagonalPixels = (float) Math.sqrt(Math.pow(screenWidth, 2) + Math.pow(screenHeight, 2));
        float screenSize = (diagonalPixels / (160f * density)) * 1f;

        Resources rs = activity.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean hasNavBarFun = false;
        if (id > 0) {
            hasNavBarFun = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavBarFun = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavBarFun = true;
            }
        } catch (Exception e) {
            hasNavBarFun = false;
        }
        return hasNavBarFun;
    }

    /**
     * <br> Description: 获取虚拟按键高度
     * <br> Date:        2017/8/16 16:54
     */
    public static int getNavigationBarHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(realDisplayMetrics);
        } else {
            Class c;
            try {
                c = Class.forName("android.view.Display");
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, realDisplayMetrics);
            } catch (Exception e) {
                realDisplayMetrics.setToDefaults();
                e.printStackTrace();
            }
        }
        int screenRealHeight = realDisplayMetrics.heightPixels;
        int screenRealWidth = realDisplayMetrics.widthPixels;
        return screenRealHeight - screenHeight;    //screenRealHeight上面方法中有计算
    }

    /**
     * <br> Description: 检查在有虚拟按键的设备上面，虚拟按键是否展示出来（收起或展示）
     * <br> Date:        2017/8/16 16:55
     */
    public static boolean checkDeviceHasNavigationBar(Activity activity) {
        return getNavigationBarHeight(activity) > 0;//screenRealHeight上面方法中有计算
    }

    /**
     * <br> Description: 通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
     * <br> Date:        2017/8/16 16:55
     */
    public static boolean checkDeviceHasNavigationBar(Context activity) {


        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    /**
     * <br> Description: 获取状态栏的高度
     * <br> Date:        2017/8/16 16:55
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * <br> Description: 隐藏虚拟按键，并且全屏
     * <br> Date:        2017/10/25 16:55
     */
    public static void hideBottomUIMenu(@NonNull Activity mActivity) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = mActivity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = mActivity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    /**
     * <br> Description: 退出全屏时显示系统导航栏、状态栏
     * <br> Date:        2017/10/25 16:55
     */
    public static void showSystermUi(@NonNull Activity mActivity){
        mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }
}
