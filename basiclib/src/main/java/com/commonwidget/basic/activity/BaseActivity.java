package com.commonwidget.basic.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by huangshunbo on 2017/11/24.
 * 1.沉浸式处理（Activity相关设置 是否允许旋转 状态栏颜色）
 * 2.空数据感情图处理
 * 3.懒加载
 * 4.软键盘处理
 * 5.启动Activity处理
 * 6.数据加载中动画
 * 7.Toolbar封装
 * 8.防止快速点击
 * 9.布局处理
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    //是否沉浸式
    private boolean isSteepStatuBar = true;
    //沉浸式颜色
    private int steepStatuBarColor = 0;
    //是否允许旋转
    private boolean isAllowScreenRotate = false;

    //点击时间
    private long lastClickTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initView());
    }


    //9.布局处理
    public abstract int initView();
    //1.沉浸式处理
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }
    //5.启动Activity处理
    public void startActivity(Class<?> clz){
        startActivity(clz,null);
    }
    public void startActivity(Class<?> clz,Bundle bundle){
        startActivityForResult(clz,-1,bundle);
    }
    public void startActivityForResult(Class<?> clz, int requestCode,Bundle bundle) {
        Intent intent = new Intent(this,clz);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }
    //8.防止快速点击
    private boolean fastClick() {

        if (System.currentTimeMillis() - lastClickTime <= 1000) {
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }
    @Override
    public void onClick(View v) {
        if(fastClick()){
            onActivityClick(v);
        }
    }
    public void onActivityClick(View v){}
}
