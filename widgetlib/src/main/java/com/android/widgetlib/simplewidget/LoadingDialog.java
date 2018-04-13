package com.android.widgetlib.simplewidget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import commonwidget.android.com.widgetlib.R;

public class LoadingDialog extends Dialog{

    Context mContext;
    private static ObjectAnimator animator ;
    private static LoadingDialog mLoadingDialog;

    private LoadingDialog(Context context){
        super(context);
        this.mContext = context;

    }

    public static void showLoading(Context context){
        ImageView loading = new ImageView(context);
        loading.setImageResource(R.drawable.loading);
        animator = ObjectAnimator.ofFloat(loading, "rotation", 0f, 360f);
        animator.setDuration(300);
        animator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        showLoading(context,loading);
    }

    public static void showLoading(Context context, View content) {
        mLoadingDialog = new LoadingDialog(context);
        mLoadingDialog.setContentView(content);
        mLoadingDialog.show();
    }

    public static void closeLoading(){
        close();
    }
    public static void closeLoading(Object ...obj){
        close();
        if(obj != null && obj.length>0){
            for(int i=0;i<obj.length;i++){
                obj[i] = null;
            }
        }

    }

    private static void close(){
        if(mLoadingDialog != null){
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        if(animator != null && animator.isRunning()){
            animator.cancel();
        }
    }

}
