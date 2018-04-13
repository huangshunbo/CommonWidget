package com.android.widgetlib.fold;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import commonwidget.android.com.widgetlib.R;

public class FlodView extends LinearLayout implements View.OnClickListener{

    View flodView;
    View contentView;
    Context context;
    Animation inAnimation,outAnimation;
    OnFlodChangeListener listener;
    private void initView() {
        flodView = getChildAt(0);
        contentView = getChildAt(1);
        if(flodView == null || contentView == null){
            throw new NullPointerException("first child or second child is null");
        }
        flodView.setOnClickListener(this);
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.flod_top_in);
        outAnimation = AnimationUtils.loadAnimation(context,R.anim.flod_top_out);
        if(listener != null){
            listener.onContentVisiable(contentView.getVisibility()== View.VISIBLE);
        }
        setOrientation(VERTICAL);
        setBackgroundColor(Color.WHITE);
    }

    protected void onInitContentView() {
    }

    public void setListener(OnFlodChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    public FlodView(Context context) {
        super(context);
        this.context = context;
        onInitContentView();
    }

    public FlodView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        onInitContentView();
    }

    public FlodView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        onInitContentView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlodView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        onInitContentView();
    }

    @Override
    public void onClick(View v) {
        if(v == flodView){
            if(isVisible(contentView)){
//                contentView.startAnimation(outAnimation);
                contentView.setVisibility(View.GONE);
                if (listener != null){
                    listener.onContentVisiable(false);
                }
            }else {
//                contentView.startAnimation(inAnimation);
                contentView.setVisibility(View.VISIBLE);
                if (listener != null){
                    listener.onContentVisiable(true);
                }
            }
        }
    }


    private boolean isVisible(View view){
        if(view.getVisibility() == View.VISIBLE){
            return true;
        }
        return false;
    }
    interface OnFlodChangeListener
    {
        void onContentVisiable(boolean isVisiable);
    }
}
