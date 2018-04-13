package com.android.widgetlib.fold;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import commonwidget.android.com.widgetlib.R;


public class TitleFlodView extends FlodView implements FlodView.OnFlodChangeListener{

    TextView tvTitle;
    ImageView ivArrow;
    String title;
    float titleSize;
    int titleColor;
    ObjectAnimator indicateOpenAnim,indicateCloseAnim;

    public TitleFlodView(Context context) {
        super(context);
        initAttr(null);
    }
    public TitleFlodView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    public TitleFlodView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    public TitleFlodView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TitleFlodView);
        title = a.getString(R.styleable.TitleFlodView_title_txt);
        titleSize = a.getDimensionPixelSize(R.styleable.TitleFlodView_title_size, 15);
        titleColor = a.getColor(R.styleable.TitleFlodView_title_color, Color.BLACK);
        tvTitle.setText(title);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSize);
        tvTitle.setTextColor(titleColor);

    }

    @Override
    protected void onInitContentView() {
        super.onInitContentView();
        View view= View.inflate(context,R.layout.flod_title,null);
        addView(view,0);
        tvTitle = (TextView) view.findViewById(R.id.flod_title);
        ivArrow = (ImageView) view.findViewById(R.id.flod_arrow);
        setListener(this);
        indicateOpenAnim = ObjectAnimator.ofFloat(ivArrow, "rotation", 0f, -180f);
        indicateOpenAnim.setDuration(400);
        indicateCloseAnim = ObjectAnimator.ofFloat(ivArrow, "rotation", -180f, 0f);
        indicateCloseAnim.setDuration(400);
    }

    @Override
    public void onContentVisiable(boolean isVisiable) {
        if(tvTitle == null || tvTitle == null){
            return ;
        }
        if(isVisiable){
            indicateOpenAnim.start();
        }else{
            indicateCloseAnim.start();
        }
    }
    public void setTitle(String title) {
        this.title = title;
        tvTitle.setText(title);
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }
    public void updateData(){
        postInvalidate();
    }
}
