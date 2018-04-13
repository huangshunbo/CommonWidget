package com.android.widgetlib.tabviewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.basiclib.net.ImageNetUtil;
import com.android.basiclib.net.glide.GlideApp;
import com.android.widgetlib.simplewidget.NumberRedView;
import com.android.widgetlib.tool.ScreenTool;

import commonwidget.android.com.widgetlib.R;

public class TabView extends RelativeLayout{
    private static final int tabMinSize = ScreenTool.dip2px(20);
    private static final int tabMaxSize = ScreenTool.dip2px(60);
    ImageView ivIcon;
    TextView tvTitle;
    NumberRedView nrvCorner;

    public TabView(Context context,String txt, Drawable drawable, TabViewPagerManager.TAB_SIZE size) {
        super(context);
        View view = View.inflate(context, R.layout.tab, this);
        ivIcon = (ImageView) view.findViewById(R.id.main_tab_item_img);
        tvTitle = (TextView) view.findViewById(R.id.main_tab_item_txt);
        nrvCorner = (NumberRedView) view.findViewById(R.id.dotView);
        if(!TextUtils.isEmpty(txt)) tvTitle.setText(txt);
        if(drawable != null) ivIcon.setImageDrawable(drawable);

        ViewGroup.LayoutParams vp = ivIcon.getLayoutParams();
        if(size == TabViewPagerManager.TAB_SIZE.NORMAL){
            vp.width = tabMinSize;
            vp.height = tabMinSize;
        }else {
            vp.width = tabMaxSize;
            vp.height = tabMaxSize;
        }
        ivIcon.setLayoutParams(vp);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -1);
        lp.weight = 1;
        view.setLayoutParams(lp);
    }
    public void on(){
        ivIcon.setSelected(true);
        tvTitle.setSelected(true);
    }
    public void off(){
        ivIcon.setSelected(false);
        tvTitle.setSelected(false);
    }
    public void showNum(int num){
        nrvCorner.show(num,true);
    }
    public void showNetIcon(String url){
        ImageNetUtil.load(url,ivIcon);
    }
    public void showIcon(int resId){
        ivIcon.setImageResource(resId);
    }

    public void startAnim(Animation onAnimation) {
        ivIcon.startAnimation(onAnimation);
        tvTitle.startAnimation(onAnimation);
    }
}
