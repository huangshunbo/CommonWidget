package com.android.widgetlib.tool;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.basiclib.MApplication;
import com.android.basiclib.ui.util.SystemBarTintManager;

public class ScreenTool {

    public static int[] getScreenDispaly(Context context){
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int width=wm.getDefaultDisplay().getWidth();//手机屏幕的宽度
        int height=wm.getDefaultDisplay().getHeight();//手机屏幕的高度
        int result[] = {width,height};
        return result;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return int
     * @date 2014-8-2 下午3:39:07
     */
    public static int dip2px(float dpValue) {
        float scale = MApplication.application.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue
     * @return int
     * @date 2014-8-2 下午3:39:13
     */
    public static int px2dip(float pxValue) {
        float scale = MApplication.application.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue) {
        float fontScale = MApplication.application.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale（DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue) {
        float fontScale = MApplication.application.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }




}
