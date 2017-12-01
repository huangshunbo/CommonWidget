package com.android.basiclib.tip;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import commonwidget.android.com.basiclib.R;

public class ReloadTipsView extends RelativeLayout {

    private View mView;
    private LinearLayout mLLmain;
    private ImageView mSpinnerImageView;
    private TextView mTvTips, mTvTipsDesc1;
    private ImageView mImgLogo;

    public ReloadTipsView(Context context) {
        super(context);
        init(context);
    }

    public ReloadTipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mView = LayoutInflater.from(context).inflate(R.layout.view_rtv_load_tips, this, true);
        mLLmain = (LinearLayout) mView.findViewById(R.id.ll_main);
        mImgLogo = (ImageView) mView.findViewById(R.id.imgLogo);
        mTvTips = (TextView) mView.findViewById(R.id.tvTips);
        mTvTipsDesc1 = (TextView) mView.findViewById(R.id.tvTipsDesc1);
        mSpinnerImageView = (ImageView) mView.findViewById(R.id.spinnerImageView);
    }

    private void showTips() {
        mImgLogo.setVisibility(View.VISIBLE);
        mTvTips.setVisibility(View.VISIBLE);
        mTvTipsDesc1.setVisibility(View.VISIBLE);

        mView.setVisibility(View.VISIBLE);
        mSpinnerImageView.setVisibility(View.INVISIBLE);
    }

    //加载失败，提示服务器异常，加载出错了
    public void showFailureTips() {
        showTips();
        mImgLogo.setImageResource(R.drawable.empty_data_server);
        mTvTips.setText(R.string.rtv_service_tips);
        mTvTipsDesc1.setText(R.string.rtv_click_refresh_tip);
    }

    //空数据
    public void showEmptyTips() {
        showTips();
        mImgLogo.setImageResource(R.drawable.empty_data_record);
        mTvTips.setText(R.string.rtv_not_data);
        mTvTipsDesc1.setText(R.string.rtv_click_refresh_tip);
    }


    //无网络
    public void showNoNetworkTips() {
        showTips();
        mImgLogo.setImageResource(R.drawable.empty_data_internet);
        mTvTips.setText(R.string.rtv_not_net);
        mTvTipsDesc1.setText(R.string.rtv_click_screen);
    }

    //设置重新加载监听
    public void setOnReloadDataListener(final LoadTipsListener listener) {
        mLLmain.setOnClickListener(new OnClickListener() {

            @Override public void onClick(View v) {
                showProgressBar();
                listener.clickReloadData();
            }
        });
    }

    //显示load
    public void showProgressBar() {
        mView.setVisibility(View.VISIBLE);
        mSpinnerImageView.setVisibility(View.VISIBLE);
        AnimationDrawable spinner = (AnimationDrawable) mSpinnerImageView.getBackground();
        spinner.start();

        mTvTips.setVisibility(View.INVISIBLE);
        mTvTipsDesc1.setVisibility(View.INVISIBLE);
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        AnimationDrawable spinner = (AnimationDrawable) mSpinnerImageView.getBackground();
        spinner.stop();
    }

    public interface LoadTipsListener {
        void clickReloadData();
    }
}
