package com.android.widgetlib.tabviewpager;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.basiclib.MApplication;

import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commonwidget.android.com.widgetlib.R;

import static android.support.design.widget.TabLayout.MODE_FIXED;
import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

public class TabViewPagerManager {


    private OnTVPListener onTVPListener;
    private FragmentManager fragmentManager;
    private TabViewPagerManager tabViewPagerManager;
    private ViewGroup contentView;
    private Context context;
    private TabLayout top;
    private ViewPager viewPager;
    private BottomTabLayout bottom;
    private List<Fragment> fragmentList;
    private TabPagerAdapter tabPagerAdapter;

    //TAB在底部或顶部
    private ORI_STYLE style;
    //TAB在顶部的模式 MODE_SCROLLABLE, MODE_FIXED
    private int topMode = MODE_SCROLLABLE;
    private Drawable tabBackgroundDrawable;
    private List<Drawable> defaultDrawableList = new ArrayList<>();
    private List<String> defaultTxtList = new ArrayList<>();
    private List<Integer> tabSizeList = new ArrayList<>();
    private Animation onAnimation;

    private TabViewPagerManager(Context context, ViewGroup contentView, List<Fragment> fragmentList, OnTVPListener listener, FragmentManager fragmentManager){
        this.context = context;
        this.contentView = contentView;
        this.fragmentList = fragmentList;
        this.onTVPListener = listener;
        this.fragmentManager = fragmentManager;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
        View content = View.inflate(context, R.layout.tab_viewpager_content,null);
        top = (TabLayout) content.findViewById(R.id.top);
        viewPager = (ViewPager) content.findViewById(R.id.viewpager);
        bottom = new BottomTabLayout();
        RelativeLayout bottomRel = (RelativeLayout) content.findViewById(R.id.bottom);
        LinearLayout bottomContent = (LinearLayout) content.findViewById(R.id.bottom_content);
        ImageView bottomBackground = (ImageView) content.findViewById(R.id.bottom_background);
        bottomRel.bringToFront();

        int count = 0;
        if(defaultTxtList!= null && defaultTxtList.size()>0){
            count = defaultTxtList.size();
        }else if(defaultDrawableList != null && defaultDrawableList.size()>0){
            count = defaultDrawableList.size();
        }
        tabPagerAdapter = new TabPagerAdapter(fragmentManager,fragmentList,count,defaultTxtList);
        viewPager.setAdapter(tabPagerAdapter);
        if(style == ORI_STYLE.BOTTOM){
            top.setVisibility(View.GONE);
            bottomBackground.setImageDrawable(tabBackgroundDrawable);
            bottom.setup(context,bottomContent,defaultTxtList,defaultDrawableList,tabSizeList,viewPager);
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
                public void onPageSelected(int position) {
                    bottom.on(position);
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            bottom.on(0);
            if(onAnimation != null){
                bottom.animation(onAnimation);
            }
        }else {
            bottomRel.setVisibility(View.GONE);
            for(int i=0;i < count;i++){
                TabLayout.Tab tab = top.newTab();
                if(defaultTxtList!=null && defaultTxtList.get(i)!=null) tab.setText(defaultTxtList.get(i));
                if(defaultDrawableList!=null && defaultDrawableList.get(i)!=null) tab.setIcon(defaultDrawableList.get(i));
                top.addTab(tab);
            }
            top.setupWithViewPager(viewPager);
            top.setTabsFromPagerAdapter(tabPagerAdapter);
            top.setBackground(tabBackgroundDrawable);
            top.setTabMode(topMode);
        }
        contentView.addView(content);
        viewPager.setCurrentItem(0);

    }

    public void updateTabIcon(int index,int resId){
        bottom.showIcon(index,resId);
    }
    public void updateTabIcon(int ...args){
        for (int i=0;i<args.length;i++){
            bottom.showIcon(i,args[i]);
        }
    }
    public void updateTabNetIcon(int index,String url){
        bottom.showNetIcon(index,url);
    }
    public void updateTabNetIcon(String ...urls){
        for(int i=0;i<urls.length;i++){
            bottom.showNetIcon(i,urls[i]);
        }
    }
    public void updateTabNetIcon(List<String> urls){
        for(int i=0;i<urls.size();i++){
            bottom.showNetIcon(i,urls.get(i));
        }
    }
    public void updateTabNum(int index,int num){
        bottom.showNum(index,num);
    }

    public static class Builder
    {
        private TabViewPagerManager tabViewPagerManager;



        public Builder(Context context,ViewGroup contentView, List<Fragment> fragmentList, OnTVPListener listener, FragmentManager fragmentManager){
            this.tabViewPagerManager = new TabViewPagerManager(context,contentView,fragmentList,listener,fragmentManager);
            style(ORI_STYLE.BOTTOM);
        }
        public Builder style(ORI_STYLE style){
            this.tabViewPagerManager.style = style;
            return this;
        }
        public Builder defaultTabDrawables(Drawable ...args){
            this.tabViewPagerManager.defaultDrawableList.clear();
            for (Drawable arg:args) {
                this.tabViewPagerManager.defaultDrawableList.add(arg);
            }
            return this;
        }
        public Builder defaultTabDrawables(List<Drawable> args){
            this.tabViewPagerManager.defaultDrawableList.clear();
            this.tabViewPagerManager.defaultDrawableList.addAll(args);
            return this;
        }
        public Builder defaultTabDrawables(int ...args){
            this.tabViewPagerManager.defaultDrawableList.clear();
            for (int arg:args) {
                Drawable drawable = MApplication.application.getDrawable(arg);
                this.tabViewPagerManager.defaultDrawableList.add(drawable);
            }
            return this;
        }
        public Builder defaultTabTxts(List<String> args){
            this.tabViewPagerManager.defaultTxtList.clear();
            this.tabViewPagerManager.defaultTxtList.addAll(args);
            return this;
        }
        public Builder defaultTabTxts(String ...args){
            this.tabViewPagerManager.defaultTxtList.clear();
            for (String arg:args) {
                this.tabViewPagerManager.defaultTxtList.add(arg);
            }
            return this;
        }
        public Builder defaultTabBackgroundWithColor(int color){
            this.tabViewPagerManager.tabBackgroundDrawable = new ColorDrawable(color);
            return this;
        }
        public Builder defaultTabBackground(Drawable drawable){
            this.tabViewPagerManager.tabBackgroundDrawable = drawable;
            return this;
        }
        public Builder defaultTabBackgroundWithBitmap(int resid){
            this.tabViewPagerManager.tabBackgroundDrawable = MApplication.application.getDrawable(resid);
            return this;
        }
        public Builder tabBigSize(int i) {
            this.tabViewPagerManager.tabSizeList.add(i);
            return this;
        }
        public Builder animation(int animId){
            this.tabViewPagerManager.onAnimation = AnimationUtils.loadAnimation(MApplication.application,animId);
            return this;
        }
        public Builder topMode(@TabLayout.Mode int mode){
            this.tabViewPagerManager.topMode = mode;
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public TabViewPagerManager build(){
            this.tabViewPagerManager.init();
            return this.tabViewPagerManager;
        }


    }



    public enum ORI_STYLE{
        TOP,BOTTOM
    }
    public enum TAB_SIZE{
        BIG,NORMAL
    }

    public interface OnTVPListener
    {

    }
}
