package com.android.widgetlib.gesturecipher;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.widgetlib.tool.ScreenTool;

import commonwidget.android.com.widgetlib.R;

public class GestureView extends ViewGroup implements Lines.LinesCallback{

    private static final int ERROR_PWD_TOO_SHORT = -1;
    private static final int ERROR_PWD_TWO_DIFF = -2;
    private static final int PWD_DEFAULT_LEN = 5;
    private int minPasswordPoint = PWD_DEFAULT_LEN;
    private String firstNum = "";
    private String secondNum = "";
    private IGesturePasswordListener mIgesturePasswordListener;
    SparseArray<Point> pointList = new SparseArray<>(9);
    private int screenWidth;
    private int screenHeigh;
    private int pointRadius;
    private Context context;
    private Lines lines;
    private ViewGroup parentView;


    public void setmIgesturePasswordListener(IGesturePasswordListener mIgesturePasswordListener) {
        this.mIgesturePasswordListener = mIgesturePasswordListener;
    }



    /**
     *<br> Description: 设置最小的密码位数
     *<br> Author:      huangshunbo
     *<br> Date:        2017/10/26 14:58
     */
    public void setMinPasswordPoint(int minPasswordPoint) {
        this.minPasswordPoint = minPasswordPoint;
    }
    /**
     *<br> Description: 重置密码
     *<br> Author:      huangshunbo
     *<br> Date:        2017/10/26 14:58
     */
    public void reset(){
        firstNum = "";
        secondNum = "";
    }

    public GestureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        int[] screen = ScreenTool.getScreenDispaly(context);
        screenWidth = screen[0];
        screenHeigh = screen[1];
        init();
        setBackgroundResource(R.drawable.sky);
        lines.setLinesCallback(this);
    }
    private void init() {
        lines = new Lines(context,pointList);
        for(int i= 0 ; i < 9 ; i++){
            Point point;
            ImageView imageView = new ImageView(context);
            addView(imageView);
            // 第几行
            int row = i / 3;
            // 第几列
            int col = i % 3;
            //高度分成12份，手势密码占据底部6份,
            int heightDistance = screenHeigh/12;
            //宽度分成六份，每个Point占两份
            int widthDistance = screenWidth/6;
            pointRadius = widthDistance/2;
            point = new Point(context,imageView,widthDistance+col*widthDistance*2,heightDistance*4+heightDistance +row*heightDistance*2,pointRadius,i+1);
            pointList.put(i,point);
        }
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for(int i = 0 ; i < getChildCount() ; i++){
            View child = getChildAt(i);
            Point point = pointList.get(i);
            child.layout(point.getLeftX(),point.getTopY(),point.getRightX(),point.getBottomY());
        }
    }

    public void setParentView(ViewGroup parentView) {
        this.parentView = parentView;
        this.parentView.addView(lines,new LinearLayout.LayoutParams(-1,-1));
        this.parentView.addView(this,new LinearLayout.LayoutParams(-1,-1));
    }

    @Override
    public void onStart() {
        mIgesturePasswordListener.onGesCipherStart();
    }

    @Override
    public void onComplete(String pwd) {
        if(pwd.length()<minPasswordPoint){
            mIgesturePasswordListener.onError(ERROR_PWD_TOO_SHORT,"密码太短",pwd);
        }else {
            if(TextUtils.isEmpty(firstNum)){
                firstNum = pwd;
                mIgesturePasswordListener.onFirstPwd(firstNum);
            }else if(TextUtils.isEmpty(secondNum)){
                secondNum = pwd;
                if(firstNum.equals(secondNum)){
                    mIgesturePasswordListener.onSecondPwd(pwd);
                    reset();
                }else {
                    mIgesturePasswordListener.onError(ERROR_PWD_TWO_DIFF,"两次绘制的密码不同，重新绘制","firstPwd:"+firstNum+" secondPwd:"+secondNum);
                    reset();
                }
            }
        }
    }

    public interface IGesturePasswordListener
    {
        void onGesCipherStart();
        void onError(int code, String msg, String pwd);
        void onFirstPwd(String pwd);
        void onSecondPwd(String pwd);
    }

}
