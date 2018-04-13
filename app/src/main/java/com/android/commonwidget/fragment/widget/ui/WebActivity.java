package com.android.commonwidget.fragment.widget.ui;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.basiclib.ui.view.AbstractMvpActivity;
import com.tencent.smtt.sdk.WebView;

@Route(path = WebActivity.AROUTER_TAG)
public class WebActivity extends AbstractMvpActivity {

    public static final String AROUTER_TAG = "/commonwidget/WebActivity";
    WebView mWebView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mWebView = new WebView(this);
        setContentView(mWebView);
        mWebView.loadUrl("http://m.budejie.com");

    }
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
}
