package com.android.widgetlib.tbdetail;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import com.android.basiclib.log.LogUtil;

public class ConflictScrollView extends ScrollView {
    private static final int TOUCH_IDLE = 0;
    private static final int TOUCH_INNER_CONSIME = 1; // touch事件由ScrollView内部消费
    private static final int TOUCH_DRAG_LAYOUT = 2; // touch事件由上层的DragLayout去消费
    private static final String TAG = "leon";

    private int scrollMode;
    private float  downX;
    private float  downY;
    private int mTouchSlop = 4; // 判定为滑动的阈值，单位是像素
    boolean isAtTop = true;

    boolean needHorizontalScroll=false; //false 由父控件消耗横向滑动， true自己消耗横向滑动
    boolean isScrolledToTop = false;
    boolean isScrolledToBottom = false;

    public ConflictScrollView(Context context) {
        this(context,null);
    }

    public ConflictScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ConflictScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downX = ev.getRawX();
            downY = ev.getRawY();
            isAtTop = isAtTop();
            float y = (float)getScaleY();
            LogUtil.d("hsb getScrollY : "+ y +"isAtTop "+isAtTop);
            scrollMode = TOUCH_IDLE;
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (scrollMode == TOUCH_IDLE) {
                float xDistance = Math.abs(downX - ev.getRawX());

                float yOffset = downY - ev.getRawY();
                float yDistance = Math.abs(yOffset);
                Log.d(TAG, "BottomScrollView dispatchTouchEvent yOffset:"+yOffset+" yDistance:"+yDistance+" mTouchSlop:"+mTouchSlop+" isAtTop:"+isAtTop);
                if (xDistance > yDistance && xDistance > mTouchSlop) {
                    //横向滑动交给父控件处理
                    scrollMode = TOUCH_DRAG_LAYOUT;
                    getParent().requestDisallowInterceptTouchEvent(needHorizontalScroll);
                } else if (yDistance > xDistance && yDistance > mTouchSlop) {
                    if (yOffset < 0 && isAtTop) {
                        scrollMode = TOUCH_DRAG_LAYOUT;
                        //在顶部的向下滑动，交给父控件处理
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                    } else {
                        scrollMode = TOUCH_INNER_CONSIME;
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 这个log可以研究ScrollView的上下padding对结果的影响
        System.out.println("onScrollChanged getScrollY():" + getScrollY() + " t: " + t + " paddingTop: " + getPaddingTop());

        if (getScrollY() == 0) {
            isScrolledToTop = true;
            isScrolledToBottom = false;
            System.out.println("onScrollChanged isScrolledToTop:" + isScrolledToTop);
        } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
            isScrolledToBottom = true;
            System.out.println("onScrollChanged isScrolledToBottom:" + isScrolledToBottom);
            isScrolledToTop = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = false;
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollMode == TOUCH_DRAG_LAYOUT) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 判断WebView是否在顶部
     *
     * @return 是否在顶部
     */
    public boolean isAtTop() {

        return getScrollY() == 0;
    }

}
