package com.android.basiclib.log;

import android.content.pm.ApplicationInfo;

import com.apkfuns.logutils.LogUtils;

import com.android.basiclib.MApplication;
import com.android.basiclib.util.SystemUtil;

public class LogUtil {

    //debug模式下输出log
    private static final boolean isDebug = isApkDebugable();
    static {
        LogUtils.getLogConfig().configAllowLog(true)
                .configTagPrefix(SystemUtil.getApplicationName())
                .configShowBorders(true)
                //方法偏移量,这里由于LogUtil工具的封装需调用两层才会调用到真正的打印方法，该偏移量可以使得打印调用方的函数堆栈偏移打印出真正的调用者
                .configMethodOffset(2)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");
    }

    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info= MApplication.application.getApplicationInfo();
            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {

        }
        return false;
    }

    public static void v(Object msg){
        Exception exception = new Exception();
        StackTraceElement[] trace = exception.getStackTrace();
        String tag = trace[0].getClassName()+trace[0].getMethodName()+trace[0].getLineNumber();
        v(tag,msg);
    }
    public static void v(String tag,Object msg){
        LogUtils.v(msg);
    }

    public static void d(Object msg){
        Exception exception = new Exception();
        StackTraceElement[] trace = exception.getStackTrace();
        String tag = trace[0].getClassName()+trace[0].getMethodName()+trace[0].getLineNumber();
        d(tag,msg);
    }
    public static void d(String tag,Object msg){
        LogUtils.d(msg);
    }

    public static void i(Object msg){
        Exception exception = new Exception();
        StackTraceElement[] trace = exception.getStackTrace();
        String tag = trace[0].getClassName()+trace[0].getMethodName()+trace[0].getLineNumber();
        i(tag,msg);
    }
    public static void i(String tag,Object msg){
        LogUtils.i(msg);
    }

    public static void w(Object msg){
        Exception exception = new Exception();
        StackTraceElement[] trace = exception.getStackTrace();
        String tag = trace[0].getClassName()+trace[0].getMethodName()+trace[0].getLineNumber();
        w(tag,msg);
    }
    public static void w(String tag,Object msg){
        LogUtils.w(msg);
    }

    public static void e(Object msg){
        Exception exception = new Exception();
        StackTraceElement[] trace = exception.getStackTrace();
        String tag = trace[0].getClassName()+trace[0].getMethodName()+trace[0].getLineNumber();
        e(tag,msg);
    }
    public static void e(String tag,Object msg){
        LogUtils.e(msg);
    }


}
