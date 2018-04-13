package com.android.commonwidget.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.android.basiclib.fragment.AbstractMvpFragment;
import com.android.basiclib.log.LogUtil;
import com.android.basiclib.net.ImageNetUtil;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.inflate.EWidget;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.commonwidget.R;
import com.android.commonwidget.contract.IMainActivityContract;
import com.android.commonwidget.presenter.MainPresenter;
import com.android.widgetlib.simplewidget.CustomDialog;

import java.util.Arrays;

import cn.bingoogolapple.bgabanner.BGABanner;

@ELayout(Layout = R.layout.fragment_home)
public class HomeFragment extends AbstractMvpFragment implements IMainActivityContract.IView{

    MainPresenter mainPresenter;
    @EWidget(id = R.id.banner_guide_content)
    BGABanner mBanner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle("测试标题")
                .setMessage("测试消息体测试消息体测试消息体测试消息体测试消息体测试消息体测试消息体")
                .setCancelButton("取消啦", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setSubmitButton("确认啦", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).create();
        customDialog.show();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainPresenter.requestData();

        mBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                ImageNetUtil.load(model,R.drawable.default_banner_img,itemView);
            }
        });

        mBanner.setData(Arrays.asList(
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513341165939&di=85399b17c614e4757058ed31fe564f95&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0147a858edf65da8012049ef691576.jpg%402o.jpg"
                ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513340555148&di=104f38872b4e283b40e9d01dc7c0559e&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01f14657834af70000018c1b211b79.jpg"
                ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513340594433&di=1bad44d36f9f50b0e250f057ac1879eb&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01f94156df99e56ac72531cb8e9078.jpg"
        ), Arrays.asList("提示文字1", "提示文字2", "提示文字3"));


    }

    @Override
    protected boolean isisIntrusionStatuBar() {
        return true;
    }

    @Override
    public void onActivityClick(View v) {
        super.onActivityClick(v);

    }


    @Override
    public void showData() {
        LogUtil.d("成功获取数据");
    }



    @Override
    protected IBasePresenter createPresenter() {
        mainPresenter = new MainPresenter();
        return mainPresenter;
    }


}
