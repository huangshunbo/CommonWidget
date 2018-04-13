package com.android.commonwidget.fragment.widget.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.basiclib.log.LogUtil;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.inflate.EWidget;
import com.android.basiclib.ui.mvp.IBasePresenter;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import com.android.commonwidget.R;
import com.android.commonwidget.activity.BaseActivity;

@ELayout(Layout = R.layout.activity_scan)
@Route(path = ScanActivity.AROUTER_TAG)
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {

    public static final String AROUTER_TAG = "/commonwidget/ScanActivity";

    @EWidget(id = R.id.zxingview)
    ZXingView zXingView;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingView.setDelegate(this);
        zXingView.startCamera();
        zXingView.showScanRect();
        zXingView.startSpot();
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        LogUtil.d("扫描成功：---------   " + result + "   ---------");
        zXingView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "---------  onScanQRCodeOpenCameraError  ---------", Toast.LENGTH_SHORT).show();
        LogUtil.d("---------  onScanQRCodeOpenCameraError  ---------");
    }
}
