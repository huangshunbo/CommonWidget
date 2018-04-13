package com.android.widgetlib.gesturecipher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import commonwidget.android.com.widgetlib.R;


public class Point {

    private static final String TAG = "Point";
    private ImageView imageView;
    //Point左边与屏幕左边的距离
    private int leftX;
    //Point右边与屏幕左边的距离
    private int rightX;
    //Point顶部与屏幕顶部的距离
    private int topY;
    //Point底部与屏幕顶部的距离
    private int bottomY;
    private int centerX;
    private int centerY;
    //Point的数值
    private int num;
    //是否被选中
    private boolean isSelect;
    private Bitmap selectBitmap;
    private Bitmap unSelectBitmap;
    private Context context;

    public Point(Context context, ImageView imageView, int centerX, int centerY, int radius, int num) {
        this.imageView = imageView;
        this.num = num;
        this.context = context;
        this.centerX = centerX;
        this.centerY = centerY;
        this.leftX = centerX - radius;
        this.rightX = centerX + radius;
        this.topY = centerY - radius;
        this.bottomY = centerY + radius;
        unSelectBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gesture_node_normal);
        selectBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.gesture_node_highlighted);
        this.imageView.setImageBitmap(unSelectBitmap);

    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getLeftX() {
        return leftX;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public int getRightX() {
        return rightX;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public int getTopY() {
        return topY;
    }

    public void setTopY(int topY) {
        this.topY = topY;
    }

    public int getBottomY() {
        return bottomY;
    }

    public void setBottomY(int bottomY) {
        this.bottomY = bottomY;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        if(isSelect){
            imageView.setImageBitmap(selectBitmap);
            Log.d(TAG,"select " + num);
        } else {
            imageView.setImageBitmap(unSelectBitmap);
            Log.d(TAG,"unselect " + num);
        }
    }
}
