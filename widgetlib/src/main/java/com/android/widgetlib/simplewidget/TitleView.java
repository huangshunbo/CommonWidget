package com.android.widgetlib.simplewidget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.basiclib.util.SystemUtil;

import commonwidget.android.com.widgetlib.R;


public class TitleView extends FrameLayout implements View.OnClickListener {

    private Context mContext;
    private ImageView ivBackIcon;
    private TextView tvBackTxt;
    private AutoWrapTextView tvTitle;
    private ImageView ivRightIcon1;
    private ImageView ivRightIcon2;

    private Drawable backIcon;
    private String backTxt;
    private String title;
    private Drawable rightIcon1;
    private Drawable rightIcon2;

    private TitleListener mTitleListener;

    public TitleView(Context context) {
        super(context);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray ta= mContext.obtainStyledAttributes(attrs,R.styleable.TitleView);
        backIcon = ta.getDrawable(R.styleable.TitleView_back_cion);
        backTxt = ta.getString(R.styleable.TitleView_back_txt);
        title = ta.getString(R.styleable.TitleView_title);
        rightIcon1 = ta.getDrawable(R.styleable.TitleView_right_cion1);
        rightIcon2 = ta.getDrawable(R.styleable.TitleView_right_cion2);

        setImageView(ivBackIcon,backIcon);
        setTextView(tvBackTxt,backTxt);
        setTextView(tvTitle,title);
        setImageView(ivRightIcon1,rightIcon1);
        setImageView(ivRightIcon2,rightIcon2);
    }
    private void init(Context context){
        mContext = context;
        inflate(mContext, R.layout.title_view_layout,this);
        ivBackIcon = findViewById(R.id.title_view_back_icon);
        tvBackTxt = findViewById(R.id.title_view_back_txt);
        tvTitle = findViewById(R.id.title_view_title);
        ivRightIcon1 = findViewById(R.id.title_view_right_icon1);
        ivRightIcon2 = findViewById(R.id.title_view_right_icon2);

        ivBackIcon.setOnClickListener(this);
        tvBackTxt.setOnClickListener(this);
        ivRightIcon1.setOnClickListener(this);
        ivRightIcon2.setOnClickListener(this);
    }

    public void setBackIcon(int resId){
        setImageView(ivBackIcon,getResources().getDrawable(resId));
    }
    public void setBackIcon(Drawable drawable){
        setImageView(ivBackIcon,drawable);
    }
    public void setBackTxt(String txt){
        setTextView(tvBackTxt,txt);
    }
    public void setTitle(String title){
        setTextView(tvTitle,title);
    }
    public void setRightIcon1(int resId){
        setImageView(ivRightIcon1,getResources().getDrawable(resId));
    }
    public void setRightIcon1(Drawable drawable){
        setImageView(ivRightIcon1,drawable);
    }
    public void setRightIcon2(int resId){
        setImageView(ivRightIcon2,getResources().getDrawable(resId));
    }
    public void setRightIcon2(Drawable drawable){
        setImageView(ivRightIcon2,drawable);
    }

    public void setBackgroundColorWithNavigation(int color, Activity activity) {
        super.setBackgroundColor(color);
        SystemUtil.setStatusBarColor(color,activity);
    }


    public void setTitleListener(TitleListener mTitleListener) {
        this.mTitleListener = mTitleListener;
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_view_back_icon) {
            mTitleListener.onBackIconClick();
        } else if (i == R.id.title_view_back_txt) {
            mTitleListener.onBackTxtClick();
        } else if (i == R.id.title_view_right_icon1) {
            mTitleListener.onRightIcon1Click();
        } else if (i == R.id.title_view_right_icon2) {
            mTitleListener.onRightIcon2Click();
        }
    }

    private void setTextView(TextView tv, String txt) {
        if(!TextUtils.isEmpty(txt)){
            tv.setVisibility(VISIBLE);
            tv.setText(txt);
        }
    }

    private void setImageView(ImageView iv,Drawable drawable) {
        if(drawable != null){
            iv.setVisibility(VISIBLE);
            iv.setImageDrawable(drawable);
        }
    }



    public static class TitleListener
    {
        public void onBackIconClick(){}
        public void onBackTxtClick(){}
        public void onRightIcon1Click(){}
        public void onRightIcon2Click(){}
    }


}
