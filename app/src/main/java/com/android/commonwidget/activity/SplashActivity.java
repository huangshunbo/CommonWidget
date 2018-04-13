package com.android.commonwidget.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.basiclib.net.download.DownloadManager;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.basiclib.ui.view.AbstractMvpActivity;
import com.android.commonwidget.R;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity {
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    TabPageIndicator tpi;
    ViewPager vp;
    List<Fragment> contentList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        tpi = (TabPageIndicator) findViewById(R.id.indicator);
        vp = (ViewPager) findViewById(R.id.viewpager);
//        ARouter.getInstance().build(MainActivity.AROUTER_TAG).navigation();
        contentList.add(new ListFragment());
        contentList.add(new ListFragment());

        vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tpi.setViewPager(vp);

        ARouter.getInstance().build(MainActivity.AROUTER_TAG).navigation(this);
    }

    class MyAdapter extends FragmentPagerAdapter
    {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Hello"+position;
        }

        @Override
        public Fragment getItem(int position) {
            return contentList.get(position);
        }

        @Override
        public int getCount() {
            return contentList.size();
        }
    }
}
