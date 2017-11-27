package commonwidget.android.com.basiclib.ui.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import commonwidget.android.com.basiclib.log.LogUtil;
import commonwidget.android.com.basiclib.ui.inflate.ELayout;
import commonwidget.android.com.basiclib.ui.inflate.EWidget;
import commonwidget.android.com.basiclib.ui.util.SystemBarTintManager;
import commonwidget.android.com.basiclib.widget.tip.ReloadTipsView;

/*
* 1.感情图
* 2.全局业务（登录 权限检查 手势密码）
* 3.Toast内容
* 4.沉浸式
* 5.自定义布局以及控件注解
* */
public class BaseActivity extends AppCompatActivity{

    FrameLayout mainFrameLayout;
    LinearLayout contentLinearLayout;
    ReloadTipsView tipReloadTipsView;
    private static final int STATUS_BAR_DEFAULT_COLOR = R.color.default_status_bar_color;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        checkAllFirst();
        try {
            initView(getLayoutId());
            initWidget(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setStatusBarColor(STATUS_BAR_DEFAULT_COLOR);
    }



    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
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
    public void onCheck(){

    }
    //3.Toast内容
    public void showToast(String msg){
        Snackbar.make(this.getWindow().getDecorView(),msg,Snackbar.LENGTH_SHORT).show();
    }
    //4.沉浸式
    public void setStatusBarColor(int colorID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColorLOLLIPOP(colorID);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(false);
            tintManager.setStatusBarTintColor(colorID);
        }
    }
    @TargetApi(21)
    private void setStatusBarColorLOLLIPOP(int colorID) {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(colorID);
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) winParams.flags |= bits;
        else winParams.flags &= ~bits;
        win.setAttributes(winParams);
    }
    //5.自定义布局以及控件注解
    private void initView(int layoutId) {
        mainFrameLayout = new FrameLayout(this);
        contentLinearLayout = new LinearLayout(this);
        tipReloadTipsView = new ReloadTipsView(this);
        mainFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1,-1));
        contentLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        contentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mainFrameLayout.addView(tipReloadTipsView);
        mainFrameLayout.addView(contentLinearLayout);
        contentLinearLayout.addView(View.inflate(this,layoutId,null),new LinearLayout.LayoutParams(-1,-1));
        setContentView(mainFrameLayout);
        tipReloadTipsView.setVisibility(View.GONE);
    }
    private int getLayoutId(){
        try {
            Class<? extends BaseActivity> cls = this.getClass();
            ELayout eView = cls.getAnnotation(ELayout.class);
            return eView.Layout();
        } catch (Exception e) {
            LogUtil.e("XML File Not Found!");
            throw new NullPointerException("XML File Not Found!");
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


}
