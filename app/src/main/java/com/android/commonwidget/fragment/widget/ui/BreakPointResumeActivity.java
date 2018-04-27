package com.android.commonwidget.fragment.widget.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.basiclib.log.LogUtil;
import com.android.basiclib.net.download.DownloadListener;
import com.android.basiclib.net.download.DownloadManager;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.inflate.EWidget;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.basiclib.ui.view.AbstractMvpActivity;
import com.android.commonwidget.R;

@Route(path = BreakPointResumeActivity.AROUTER_TAG)
@ELayout(Layout = R.layout.activity_break_point_resume)
public class BreakPointResumeActivity extends AbstractMvpActivity implements View.OnClickListener{
    public static final String AROUTER_TAG = "/commonwidget/BreakPointResumeActivity";

    @EWidget(id = R.id.activity_break_point_progress1)
    ProgressBar progressBar1;
    @EWidget(id = R.id.activity_break_point_start1)
    Button start1;
    @EWidget(id = R.id.activity_break_point_pause1)
    Button pause1;
    @EWidget(id = R.id.activity_break_point_cancel1)
    Button cancel1;
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    String downloadUrl = "http://fast.yingyonghui.com/ea15e89311d5a6d86a96c9fe2f358d87/5a5315cb/apk/5517960/65bf87f8f7350aea3be4350e7519bf11";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        start1.setOnClickListener(this);
        pause1.setOnClickListener(this);
        cancel1.setOnClickListener(this);

    }

    DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            LogUtil.d("download onProcess" + progress);
        }

        @Override
        public void onSuccess() {
            LogUtil.d("download onSuccess");
        }

        @Override
        public void onFailed() {
            LogUtil.d("download onFailed");
        }

        @Override
        public void onPause() {
            LogUtil.d("download onPause");
        }

        @Override
        public void onCancel() {
            LogUtil.d("download onCancel");
        }
    };
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.activity_break_point_start1:
                    DownloadManager.getInstance().start(downloadUrl,downloadListener);
                break;
            case R.id.activity_break_point_pause1:
                    DownloadManager.getInstance().pause(downloadUrl);
                break;
            case R.id.activity_break_point_cancel1:
                    DownloadManager.getInstance().cancel(downloadUrl);
                break;
        }
    }
}
