package com.android.commonwidget.productdetail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.inflate.EWidget;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.commonwidget.R;
import com.android.commonwidget.activity.BaseActivity;
import com.android.widgetlib.tabviewpager.TabViewPagerManager;
import com.android.widgetlib.tbdetail.DragLayout;

import java.util.ArrayList;
import java.util.List;

import static android.support.design.widget.TabLayout.MODE_FIXED;

@Route(path = ProductDetailActivity.AROUTER_TAG)
@ELayout(Layout = R.layout.activity_product_detail)
public class ProductDetailActivity extends BaseActivity implements TabViewPagerManager.OnTVPListener{
    public static final String AROUTER_TAG = "/commonwidget/ProductDetailActivity";

    List<Fragment> tabFragmentList = new ArrayList<>();
    TabViewPagerManager tabViewPagerManager;
    @EWidget(id = R.id.second_page_detail)
    FrameLayout frameLayout;
    @EWidget(id = R.id.draglayout_content)
    DragLayout drawerLayout;
    @EWidget(id = R.id.scroll_tip)
    TextView tvScrollTip;
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment home = getSavedInstanceFragment(OneFragment.class);
        Fragment widget = getSavedInstanceFragment(TwoFragment.class);
        Fragment me = getSavedInstanceFragment(ThreeFragment.class);
        tabFragmentList.add(home);
        tabFragmentList.add(widget);
        tabFragmentList.add(me);
        tabViewPagerManager = new TabViewPagerManager.Builder(this,frameLayout,tabFragmentList,this,getSupportFragmentManager())
                .defaultTabDrawables(R.drawable.icon_home,R.drawable.icon_widget,R.drawable.icon_me)
                .style(TabViewPagerManager.ORI_STYLE.TOP)
                .topMode(MODE_FIXED)
                .defaultTabTxts("ONE","TWO","THREE")
                .animation(R.anim.scale_out)
                .defaultTabBackgroundWithColor(Color.parseColor("#333333"))
                .build();
        drawerLayout.setNextPageListener(new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext(int pageIndex) {
                if(pageIndex==0){
                    tvScrollTip.setText("向上滑动，查看更多");
                    tvScrollTip.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_mark_detail_next_page,0);
                }else {
                    tvScrollTip.setText("向下滑动,回到顶部");
                    tvScrollTip.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_mark_detail_next_page_up,0);
                }
            }
        });
    }
}
