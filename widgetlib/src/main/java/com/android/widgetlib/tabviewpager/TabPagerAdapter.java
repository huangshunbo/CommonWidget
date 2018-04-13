package com.android.widgetlib.tabviewpager;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titles;
    private int size;

    public TabPagerAdapter(FragmentManager fm,List<Fragment> fragmentList,int size,List<String> titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.size = size;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return size;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
