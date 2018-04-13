package com.android.widgetlib.simplewidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.android.widgetlib.tool.ScreenTool;

import commonwidget.android.com.widgetlib.R;

public class NumberRedView extends View {

    private int mBgColor = Color.RED;
    private int mFontSize = ScreenTool.dip2px(10);
    private static final int MIN_EFFECTIVE_SIZE = ScreenTool.dip2px(12);
    private static final int MINI_RADUIS = ScreenTool.dip2px(4);
    private int mRadius = MIN_EFFECTIVE_SIZE;
    private int mFontColor = Color.WHITE;
    private int number;
    private boolean showMini = false;
    private Paint mBgPaint;
    private Paint mFontPaint;
    private RectF mRectF;

    private int mWidth;
    private int mHeight;

    public NumberRedView(Context context) {
        this(context, null);
    }

    public NumberRedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberRedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initPaint();
    }

    public void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberRedView);
        int n = a.length();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.NumberRedView_dot_color) {
                mBgColor = a.getColor(attr, Color.RED);
            }else if(attr == R.styleable.NumberRedView_dot_font_size){
                    mFontSize = a.getDimensionPixelSize(attr, ScreenTool.dip2px(10));
            }else if(attr == R.styleable.NumberRedView_dot_radius){
                    mRadius = a.getDimensionPixelOffset(attr, MIN_EFFECTIVE_SIZE);
            }else if(attr == R.styleable.NumberRedView_dot_font_color){
                    mFontColor = a.getColor(attr, Color.WHITE);
            }

        }
        a.recycle();
    }

    private void initPaint() {
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);
        mFontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFontPaint.setColor(mFontColor);
        mFontPaint.setTextSize(mFontSize);
        mFontPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (number < 1 && !showMini) {
            return;
        }
        if (mRectF == null)
            return;
        canvas.save();
        drawBg(canvas);
        drawText(canvas);
        canvas.restore();
        super.onDraw(canvas);
    }

    private void drawBg(Canvas canvas) {
        if (number > 0 && number < 100) {
            canvas.drawCircle(mWidth / 2.0f, mHeight / 2.0f, mRadius, mBgPaint);
        } else if (number > 99) {
            canvas.drawRoundRect(mRectF, mHeight / 2.0f, mHeight / 2.0f, mBgPaint);
        } else if (showMini) {
            canvas.drawCircle(mWidth / 2.0f, mHeight / 2.0f, MINI_RADUIS, mBgPaint);
        }
    }

    private void drawText(Canvas canvas) {
        if (number > 0) {
            Paint.FontMetricsInt fontMetrics = mFontPaint.getFontMetricsInt();
            float baseline = mRectF.top + (mRectF.bottom - mRectF.top - fontMetrics.bottom + fontMetrics.top) / 2
                    - fontMetrics.top;
            if (number < 100) {
                canvas.drawText(String.valueOf(number), mWidth / 2.0f,
                        baseline, mFontPaint);
            } else {
                canvas.drawText("99+", mWidth / 2.0f,
                        baseline, mFontPaint);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (bottom - top > 0) {
            mWidth = right - left;
            mHeight = bottom - top;
            mRectF = new RectF(0, 0, mWidth, mHeight);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 设置宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = MIN_EFFECTIVE_SIZE;
        if (specMode == MeasureSpec.EXACTLY) {// match_parent , accurate
            width = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {// wrap_content
                int desire = getPaddingLeft() + getPaddingRight()
                        + calculateWidth();
                width = Math.min(desire, specSize);
            }
        }

        /***
         * 设置高度
         */
        int height = MIN_EFFECTIVE_SIZE / 2;
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {// match_parent , accurate
            height = specSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom()
                    + calculateHeight();
            if (specMode == MeasureSpec.AT_MOST) {// wrap_content
                height = Math.min(desire, specSize);
            }
        }
        setMeasuredDimension(width, height);
    }


    private int calculateWidth() {
        return mRadius * 2 + mRadius;
    }

    private int calculateHeight() {
        return mRadius * 2;
    }

    public void show(int number, boolean showMini) {
        this.number = number;
        this.showMini = showMini;
        postInvalidate();
    }

    public int getNumber() {
        return number;
    }
}
