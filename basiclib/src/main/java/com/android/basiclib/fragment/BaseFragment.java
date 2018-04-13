package com.android.basiclib.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.basiclib.log.LogUtil;
import com.android.basiclib.tip.ReloadTipsView;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.inflate.EWidget;
import com.android.basiclib.ui.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

/*
* 1.全局业务（登录 权限检查 手势密码）
* 2.Toast内容
* 3.沉浸式
* 4.自定义布局以及控件注解
* 5.防止快速点击
* 6.软键盘处理
* 7.EventBus 默认使用，可通过isBindeEvents来修改
* */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    Activity mActivity;
    Context mContext;
    FrameLayout mainFrameLayout;
    LinearLayout contentLinearLayout;
    ReloadTipsView tipReloadTipsView;
    //点击时间
    private long lastClickTime = 0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getContext();
        checkAllFirst();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = initView(getLayoutId());
        initWidget(this,rootView);
        if(isBindEventBus()){
            EventBus.getDefault().register(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



//   1. 全局业务（登录 权限检查 手势密码）
    private void checkAllFirst(){
        onCheck();
    }
    protected void onCheck(){}
//   2. Toast内容

    protected void showToast(String msg){
        if(mActivity instanceof BaseActivity){
            ((BaseActivity) mActivity).showToast(msg);
        }
    }
//    3. 沉浸式
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mActivity instanceof BaseActivity){
            ((BaseActivity) mActivity).handleStatusBar(isisIntrusionStatuBar());
        }
    }
    protected boolean isisIntrusionStatuBar(){
        return false;
    }
//      4.自定义布局以及控件注解
    private View initView(int layoutId){
        mainFrameLayout = new FrameLayout(mContext);
        contentLinearLayout = new LinearLayout(mContext);
        tipReloadTipsView = new ReloadTipsView(mContext);
        mainFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1,-1));
        contentLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        contentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mainFrameLayout.addView(tipReloadTipsView);
        mainFrameLayout.addView(contentLinearLayout);
        contentLinearLayout.addView(View.inflate(mContext,layoutId,null),new LinearLayout.LayoutParams(-1,-1));
        tipReloadTipsView.setVisibility(View.GONE);
        return mainFrameLayout;
    }
    private int getLayoutId(){
        try {
            Class<? extends BaseFragment> cls = this.getClass();
            ELayout eView = cls.getAnnotation(ELayout.class);
            return eView.Layout();
        } catch (Exception e) {
            LogUtil.e("XML File Not Found!");
            throw new NullPointerException("XML File Not Found!");
        }
    }
    public static void initWidget(BaseFragment fragment, View fragmentRootView) {
        Field[] fields = fragment.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                EWidget viewInject = field.getAnnotation(EWidget.class);
                if (viewInject != null) {
                    int viewId = viewInject.id();
                    int parent = viewInject.parentId();
                    try {
                        field.setAccessible(true);
                        if (parent == 0) {
                            field.set(fragment,
                                    fragmentRootView.findViewById(viewId));
                        } else {
                            field.set(fragment,
                                    fragmentRootView.findViewById(parent)
                                            .findViewById(viewId));
                        }
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                }
            }
        }
    }

    //5.防止快速点击
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
    //7.EventBus
    public boolean isBindEventBus(){
        return false;
    }
}
