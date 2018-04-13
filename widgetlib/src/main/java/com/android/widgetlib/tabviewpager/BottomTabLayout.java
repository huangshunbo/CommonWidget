package com.android.widgetlib.tabviewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

class BottomTabLayout implements View.OnClickListener{
    List<TabView> tabViews = new ArrayList<>();
    ViewPager viewPager;
    Animation onAnimation;
    public void setup(Context context, LinearLayout bottomContent, List<String> defaultTxtList, List<Drawable> defaultDrawableList, List<Integer> sizes,ViewPager viewPager) {
        this.viewPager = viewPager;
        int count = 0;
        if(defaultTxtList!= null && defaultTxtList.size()>0){
            count = defaultTxtList.size();
        }else if(defaultDrawableList != null && defaultDrawableList.size()>0){
            count = defaultDrawableList.size();
        }
        for(int i=0;i<count;i++){
            TabView tabView = new TabView(context,defaultTxtList.get(i),defaultDrawableList.get(i),
                    sizes.contains(i) ? TabViewPagerManager.TAB_SIZE.BIG : TabViewPagerManager.TAB_SIZE.NORMAL);
            bottomContent.addView(tabView);
            tabView.setTag(i);
            tabView.setOnClickListener(this);
            tabViews.add(tabView);
        }
    }

    public List<TabView> getTabViews(){
        return tabViews;
    }

    public void on(int position) {
        for(TabView tabView : tabViews){
            tabView.off();
        }
        if(tabViews.get(position)!=null){
            tabViews.get(position).on();
        }
    }

    @Override
    public void onClick(View v) {
        if(onAnimation != null && v instanceof TabView){
            TabView tabView = (TabView) v;
            tabView.startAnim(onAnimation);
        }
        viewPager.setCurrentItem((Integer) v.getTag());
    }

    public void animation(Animation onAnimation) {
        this.onAnimation = onAnimation;
    }

    public void showNum(int index,int num){
        if(index>=0 && index<tabViews.size()){
            tabViews.get(index).showNum(num);
        }
    }
    public void showNetIcon(int index,String url){
        if(index>=0 && index<tabViews.size()){
            if(!TextUtils.isEmpty(url)){
                tabViews.get(index).showNetIcon(url);
            }
        }
    }
    public void showIcon(int index,int resId){
        if(index>=0 && index<tabViews.size()){
            if(resId > 0){
                tabViews.get(index).showIcon(resId);
            }
        }
    }
}