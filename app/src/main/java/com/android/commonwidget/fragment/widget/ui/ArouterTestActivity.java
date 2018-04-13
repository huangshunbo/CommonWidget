package com.android.commonwidget.fragment.widget.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.basiclib.log.LogUtil;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.mvp.IBasePresenter;

import com.android.commonwidget.R;
import com.android.commonwidget.activity.BaseActivity;


@ELayout(Layout = R.layout.activity_arouter_test)
@Route(path = ArouterTestActivity.AROUTER_TAG)
public class ArouterTestActivity extends BaseActivity {
    public static final String AROUTER_TAG = "/commonwidget/ArouterTestActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("Hello ARouter");
    }

    @Override
    public int setStatusBarColor() {
        return Color.argb(255,50,50,50);
    }

    @Override
    public boolean isBindEventBus() {
        return false;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
}
