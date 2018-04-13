package com.android.basiclib.ui.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import commonwidget.android.com.basiclib.R;

import com.android.basiclib.constact.Constact;
import com.android.basiclib.data.DLocal;
import com.android.basiclib.log.LogUtil;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.inflate.EWidget;
import com.android.basiclib.ui.util.NavigationBarTool;
import com.android.basiclib.ui.util.SystemBarTintManager;
import com.android.basiclib.tip.ReloadTipsView;
import com.android.basiclib.util.SystemUtil;

import org.greenrobot.eventbus.EventBus;

/*
* 1.感情图
* 2.全局业务（登录 权限检查 手势密码）
* 3.Toast内容
* 4.沉浸式
* 5.自定义布局以及控件注解
* 6.启动Activity处理
* 7.防止快速点击
* 9.软键盘处理
* 10.EventBus 默认使用，可通过isBindeEvents来修改
* */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    FrameLayout mainFrameLayout;
    LinearLayout contentLinearLayout;
    ReloadTipsView tipReloadTipsView;
    private static final int STATUS_BAR_DEFAULT_TRANSPARENT = Color.argb(0, 41, 51, 102);
    //点击时间
    private long lastClickTime = 0;
    private Bundle mSavedInstanceState;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mSavedInstanceState = savedInstanceState;
        checkAllFirst();
        try {
            View view = null;
            int layoutId = getLayoutId();
            if(layoutId != 0){
               view = View.inflate(this,getLayoutId(),null);
            }

            initView(view);
            initWidget(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(isBindEventBus()){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void setContentView(View view) {
        initView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        initView(View.inflate(this,layoutResID,null));
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkEveryTimeOnResumt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isBindEventBus() && EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }


    public ViewGroup getMainLayout(){
        return contentLinearLayout;
    }
    public ViewGroup getRootView(){
        return mainFrameLayout;
    }

    //1.感情图
    public void showDataError(){
        contentLinearLayout.setVisibility(View.GONE);
        tipReloadTipsView.setVisibility(View.VISIBLE);
        tipReloadTipsView.showFailureTips();
    }
    public void showDataEmpty(){
        contentLinearLayout.setVisibility(View.GONE);
        tipReloadTipsView.setVisibility(View.VISIBLE);
        tipReloadTipsView.showEmptyTips();
    }
    public void showDataNotNetwork(){
        contentLinearLayout.setVisibility(View.GONE);
        tipReloadTipsView.setVisibility(View.VISIBLE);
        tipReloadTipsView.showNoNetworkTips();
    }
    public void showDataLoading(){
        contentLinearLayout.setVisibility(View.GONE);
        tipReloadTipsView.setVisibility(View.VISIBLE);
        tipReloadTipsView.showProgressBar();
    }
    public void dissMissDataLoading(){
        contentLinearLayout.setVisibility(View.GONE);
        tipReloadTipsView.setVisibility(View.VISIBLE);
        tipReloadTipsView.setVisibility(View.GONE);
    }
    public void showDataSuccess(){
        contentLinearLayout.setVisibility(View.VISIBLE);
        tipReloadTipsView.setVisibility(View.GONE);
    }
    //2.全局业务检测
    private void checkAllFirst() {
        onCheck();
    }
    protected void onCheck(){

    }
    private void checkEveryTimeOnResumt(){
        onCheckEvenyTime();
    }

    protected void onCheckEvenyTime(){

    };

    //3.Toast内容
    public void showToast(String msg){
        Snackbar.make(this.getWindow().getDecorView(),msg,Snackbar.LENGTH_SHORT).show();
    }
    //4.沉浸式
    public boolean isIntrusionStatuBar(){
        return false;
    }
    public int setStatusBarColor(){
        return Color.argb(255, 41, 51, 102);
    }
    public void handleStatusBar(boolean flag){
        if(flag){
            SystemUtil.setStatusBarColor(STATUS_BAR_DEFAULT_TRANSPARENT,this);
            //处理状态栏高度
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(mainFrameLayout != null){
                    mainFrameLayout.setPadding(mainFrameLayout.getPaddingLeft(),
                            0,
                            mainFrameLayout.getPaddingRight(),
                            mainFrameLayout.getPaddingBottom());
                }

            }
        }else {
            SystemUtil.setStatusBarColor(setStatusBarColor(),this);
            //处理状态栏高度
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(mainFrameLayout != null) {
                    mainFrameLayout.setPadding(mainFrameLayout.getPaddingLeft(),
                            NavigationBarTool.getStatusBarHeight(this),
                            mainFrameLayout.getPaddingRight(),
                            mainFrameLayout.getPaddingBottom());
                }
            }
        }

//        mainFrameLayout.setClipToPadding(true);
//        mainFrameLayout.setFitsSystemWindows(false);

    }

    //5.自定义布局以及控件注解
    private void initView(View layout) {
        mainFrameLayout = new FrameLayout(this);
        contentLinearLayout = new LinearLayout(this);
        tipReloadTipsView = new ReloadTipsView(this);
        mainFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1,-1));
        contentLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        contentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mainFrameLayout.addView(tipReloadTipsView);
        mainFrameLayout.addView(contentLinearLayout);
        if(layout != null){
            contentLinearLayout.addView(layout,new LinearLayout.LayoutParams(-1,-1));
        }
        super.setContentView(mainFrameLayout);
        tipReloadTipsView.setVisibility(View.GONE);

        handleStatusBar(isIntrusionStatuBar());
    }
    private int getLayoutId(){
        try {
            Class<? extends BaseActivity> cls = this.getClass();
            ELayout eView = cls.getAnnotation(ELayout.class);
            return eView.Layout();
        } catch (Exception e) {
            return 0;
        }
    }
    private void initWidget(Activity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                EWidget viewInject = field.getAnnotation(EWidget.class);
                if (viewInject != null) {
                    int viewId = viewInject.id();
                    int parent = viewInject.parentId();
                    try {
                        field.setAccessible(true);
                        if (parent == 0) {
                            field.set(activity, activity.findViewById(viewId));
                        } else {
                            field.set(activity, activity.findViewById(parent)
                                    .findViewById(viewId));
                        }
                    } catch (Exception e) {
                        LogUtil.e("XML ERROR id = " + viewId);
                    }
                }
            }
        }
    }

    //6.启动Activity处理
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
    //7.防止快速点击
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
    //10.EventBus
    public boolean isBindEventBus(){
        return false;
    }

    //11.其他
    public <T extends Fragment> T getSavedInstanceFragment(Class<T> mClass) {
        Fragment mFragment = null;
        if (mSavedInstanceState != null) {
            mFragment = getSupportFragmentManager().getFragment(mSavedInstanceState, mClass.getName());
        }
        if (mFragment == null) {
            try {
                mFragment = mClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            mFragment.onAttach((Context) this);
        }
        return (T) mFragment;
    }
}
