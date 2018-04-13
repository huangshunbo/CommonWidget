package com.android.widgetlib.gesturecipher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;


import com.android.widgetlib.tool.ScreenTool;

import java.util.Vector;

public class Lines extends View {

    private static final String TAG = "Lines";
    private Point currentPoint;
    private SparseArray<Point> pointList;
    private Vector<Pair<Point,Point>> pointPairList = new Vector<>();
    private int curX,curY;
    private Paint paint;// 声明画笔
    private Canvas canvas;// 画布
    private Bitmap bitmap;// 位图
    private int screenWidth,screenHeigh;
    private String password = "";

    public Lines(Context context, SparseArray<Point> pointList) {
        super(context, null);
        this.pointList = pointList;
        int[] screen = ScreenTool.getScreenDispaly(context);
        screenWidth = screen[0];
        screenHeigh = screen[1];
        paint = new Paint();

        canvas = new Canvas();
        bitmap = Bitmap.createBitmap(screenWidth,screenHeigh, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        paint.setStyle(Paint.Style.STROKE);// 设置非填充
        paint.setStrokeWidth(10);// 笔宽5像素
        paint.setColor(Color.rgb(4, 115, 157));// 设置颜色
        paint.setAntiAlias(true);// 不显示锯齿
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        curX = (int) event.getX();
        curY = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentPoint = getPointAt(curX,curY);
                if(currentPoint != null){
                    currentPoint.setSelect(true);
                }
                password = ""+currentPoint.getNum();
                if(linesCallback != null)
                    linesCallback.onStart();
                break;
            case MotionEvent.ACTION_MOVE:
                drawAllLine();
                Point point = getPointAt(curX,curY);
                if(currentPoint == null){
                    return true;
                }
                if(point != null && !point.isSelect()){

                    for (int i = 0; i < pointList.size(); i++) {
                        Point pointTmp = pointList.get(i);
                        if (pointTmp.getNum()!=currentPoint.getNum() && pointTmp.getNum()!=point.getNum() && onSegment(currentPoint,point, pointTmp)) {
                            pointTmp.setSelect(true);
                            password += pointTmp.getNum();
                            pointPairList.add(new Pair<Point, Point>(currentPoint, pointTmp));
                            currentPoint = pointTmp;
                        }
                    }

                    point.setSelect(true);
                    password += point.getNum();
                    pointPairList.add(new Pair<Point, Point>(currentPoint,point));
                    currentPoint = point;
                }

                canvas.drawLine(currentPoint.getCenterX(),currentPoint.getCenterY(),curX,curY,paint);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                clearPoint();
                Log.d(TAG,"password: " +password);
                invalidate();
                if(linesCallback != null)
                    linesCallback.onComplete(password);
                break;
            default:
                break;
        }
        return true;
    }

    private boolean onSegment(Point Pi , Point Pj , Point Q)
    {
        if((Q.getCenterX() - Pi.getCenterX()) * (Pj.getCenterY() - Pi.getCenterY()) == (Pj.getCenterX() - Pi.getCenterX()) * (Q.getCenterY() - Pi.getCenterY())  //叉乘
                //保证Q点坐标在pi,pj之间
                && Math.min(Pi.getCenterX() , Pj.getCenterX()) <= Q.getCenterX() && Q.getCenterX() <= Math.max(Pi.getCenterX() , Pj.getCenterX())
                && Math.min(Pi.getCenterY() , Pj.getCenterY()) <= Q.getCenterY() && Q.getCenterY() <= Math.max(Pi.getCenterY() , Pj.getCenterY()))
            return true;
        else
            return false;
    }

    private void drawAllLine() {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        for (Pair<Point, Point> pair : pointPairList) {
            canvas.drawLine(pair.first.getCenterX(), pair.first.getCenterY(),
                    pair.second.getCenterX(), pair.second.getCenterY(), paint);// 画线
        }
    }

    private void clearPoint() {
        for(int i = 0; i < pointList.size() ; i++) {
            Point point = pointList.get(i);
            point.setSelect(false);
        }
        pointPairList.clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,paint);
    }

    private Point getPointAt(int x, int y) {
        for(int i = 0; i < pointList.size() ; i++){
            Point point = pointList.get(i);
            int leftX = point.getLeftX();
            int rightX = point.getRightX();
            if (!(x >= leftX && x < rightX)) {
                // 如果为假，则跳到下一个对比
                continue;
            }

            int topY = point.getTopY();
            int bottomY = point.getBottomY();
            if (!(y >= topY && y < bottomY)) {
                // 如果为假，则跳到下一个对比
                continue;
            }

            // 如果执行到这，那么说明当前点击的点的位置在遍历到点的位置这个地方
            return point;
        }
        return null;
    }

    private LinesCallback linesCallback;
    public void setLinesCallback(LinesCallback linesCallback){
        this.linesCallback = linesCallback;
    }
    interface LinesCallback
    {
        void onStart();
        void onComplete(String pwd);
    }
}
