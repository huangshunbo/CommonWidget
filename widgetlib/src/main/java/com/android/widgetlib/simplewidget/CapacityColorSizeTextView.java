package com.android.widgetlib.simplewidget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: huangshunbo
 * @Filename: CapacityColorSizeTextView
 * @Description: 智能颜色字体大小的TextView，支持对任意字段设置文字颜色和文字大小
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2018/1/12 16:33
 */
public class CapacityColorSizeTextView extends LinearLayout {
    private List<Integer> colorList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private List<Integer> sizeList = new ArrayList<>();
    private Integer curColor;
    private Integer curSize;

    public CapacityColorSizeTextView(Context context) {
        super(context);
        init();
    }

    public CapacityColorSizeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        if(getChildCount()>stringList.size()){
            for(int i=stringList.size();i<getChildCount();i++){
                removeViewAt(i);
            }
        }
        for (int i = 0; i < stringList.size(); i++) {
            if (i < getChildCount()) {
                TextView textView = (TextView) getChildAt(i);
                configTextView(textView,i);

            } else {
                TextView textView = new TextView(getContext());
                configTextView(textView,i);
                addView(textView);
            }
        }
        setOrientation(HORIZONTAL);
    }

    public CapacityColorSizeTextView configStrings(String ...strs){
        stringList.clear();
        for (int i=0;i<strs.length;i++){
            stringList.add(strs[i]);
        }
        return this;
    }

    public CapacityColorSizeTextView configColors(int ...colors){
        colorList.clear();
        for(int i=0;i<colors.length;i++){
            colorList.add(colors[i]);
        }
        return this;
    }
    public CapacityColorSizeTextView configSizes(int ...sizes){
        sizeList.clear();
        for(int i=0;i<sizes.length;i++){
            sizeList.add(sizes[i]);
        }
        return this;
    }

    private void configTextView(TextView textView,int position){
        textView.setLayoutParams(new LayoutParams(-2, -2));
        textView.setText(stringList.get(position));
        if (colorList.size() > position) {
            curColor = colorList.get(position);
        }
        if (sizeList.size() > position) {
            curSize = sizeList.get(position);
        }
        if(curColor != 0){
            textView.setTextColor(ContextCompat.getColor(getContext(), curColor));
        }
        if(curSize != 0){
            textView.setTextSize(curSize);
        }
    }
    public void commit(){
        init();
    }
}
