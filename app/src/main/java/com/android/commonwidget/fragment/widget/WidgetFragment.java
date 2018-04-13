package com.android.commonwidget.fragment.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.android.basiclib.fragment.AbstractMvpFragment;
import com.android.basiclib.fragment.BaseLazyFragment;
import com.android.basiclib.log.LogUtil;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.inflate.EWidget;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.commonwidget.R;
import com.android.commonwidget.fragment.widget.ui.ArouterTestActivity;
import com.android.commonwidget.fragment.widget.ui.BreakPointResumeActivity;
import com.android.commonwidget.fragment.widget.ui.DancingNumberActivity;
import com.android.commonwidget.fragment.widget.ui.KeyboardActivity;
import com.android.commonwidget.fragment.widget.ui.ScanActivity;
import com.android.commonwidget.fragment.widget.ui.WebActivity;
import com.android.commonwidget.contract.IMainActivityContract;
import com.android.commonwidget.fragment.widget.bean.FunItem;
import com.android.commonwidget.fragment.widget.bean.TitleItem;
import com.android.commonwidget.fragment.widget.bean.TxtItem;
import com.android.commonwidget.fragment.widget.ui.shadow.ShadowActivity;
import com.android.commonwidget.multirecycler.ItemBase;
import com.android.commonwidget.multirecycler.MultiAdapter;
import com.android.commonwidget.productdetail.ProductDetailActivity;
import com.android.widgetlib.simplewidget.LoadingDialog;
import com.android.widgetlib.simplewidget.TitleView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@ELayout(Layout = R.layout.fragment_widget)
public class WidgetFragment extends BaseLazyFragment implements MultiAdapter.OnItemClickListener,UMShareListener {

    @EWidget(id = R.id.fragment_widget_recycler_main)
    RecyclerView mRecyclerView;
    @EWidget(id = R.id.fragment_widget_titleview)
    TitleView mTitleView;

    MultiAdapter mMultiAdapter;
    String[] btTxts = {"jumpNext","share","scan","authLogin","productDetail","X5 WebView","一个虚有其表的安全键盘","Shadow","DancingNumber","断点续传"};


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onLazyLoad() {
        mTitleView.setBackgroundColorWithNavigation(Color.RED,getActivity());
        mTitleView.setTitleListener(new TitleView.TitleListener(){
            @Override
            public void onBackIconClick() {
                LogUtil.d("onBackIconClick");
            }

            @Override
            public void onBackTxtClick() {
                LogUtil.d("onBackTxtClick");
            }

            @Override
            public void onRightIcon1Click() {
                LogUtil.d("onRightIcon1Click");
            }

            @Override
            public void onRightIcon2Click() {
                LogUtil.d("onRightIcon2Click");
            }
        });
        LoadingDialog.showLoading(getContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.closeLoading();
                initRecyclerView();
            }
        },2000);
    }

    private void initRecyclerView() {
        mMultiAdapter = new MultiAdapter(mRecyclerView,iLayoutListener);
        mMultiAdapter.setOnItemClickListener(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMultiAdapter);
        initData();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    MultiAdapter.ILayoutListener iLayoutListener = new MultiAdapter.ILayoutListener() {
        @Override
        public int oncreateLayout(int viewType) {
            switch (viewType){
                case TxtItem.ITEM_TYPE:
                    return TxtItem.ITEM_LAYOUT;
                case FunItem.ITEM_TYPE:
                    return FunItem.ITEM_LAYOUT;
                case TitleItem.ITEM_TYPE:
                    return TitleItem.ITEM_LAYOUT;
            }
            return 0;
        }
    };



    private void initData() {
        List<ItemBase> itemBaseList = new ArrayList<>();
        itemBaseList.add(new TxtItem("AutoWrapTextView","这是一个测试AutoWrapTextView的示例文字，这段文字可能会比较长。this is a AutoWrapTextView test，and the string may be too long.but take it"));
        itemBaseList.add(new FunItem("huangshunbo","1991-11-09 23:00:59",
                "这是一个示例Title，这个Title也希望是两行，且包含中英文。offcause this also be a long string,then it is two lines and include en and ch",
                R.drawable.default_banner_img,
                "99999","1","9999","10086"));
        for(int i=0;i<btTxts.length;i++){
            itemBaseList.add(new TitleItem(btTxts[i]));
        }
        mMultiAdapter.setItems(itemBaseList);
    }


    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            mMultiAdapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onItemClick(View view, int position) {
        ItemBase itemBase = mMultiAdapter.getItems().get(position);
        if(itemBase instanceof TitleItem){
            TitleItem item = (TitleItem) itemBase;
            if(item.getTitle().equals(btTxts[0])){
                ARouter.getInstance().build(ArouterTestActivity.AROUTER_TAG).navigation();
            }else if (item.getTitle().equals(btTxts[1])){
                UMImage imagelocal = new UMImage(getContext(),R.mipmap.ic_launcher);
                imagelocal.setDescription("this is a um test");
                imagelocal.setTitle("um test");
                new ShareAction(getActivity()).withMedia(imagelocal)
                        .setPlatform(SHARE_MEDIA.QQ.toSnsPlatform().mPlatform)
                        .setCallback(this).share();
            }else if (item.getTitle().equals(btTxts[2])){
                ARouter.getInstance().build(ScanActivity.AROUTER_TAG).navigation();
            }else if (item.getTitle().equals(btTxts[3])){
                UMShareAPI.get(getContext()).getPlatformInfo(getActivity(),SHARE_MEDIA.QQ,umAuthListener);
            }else if (item.getTitle().equals(btTxts[4])){
                ARouter.getInstance().build(ProductDetailActivity.AROUTER_TAG).navigation();
            }else if (item.getTitle().equals(btTxts[5])){
                ARouter.getInstance().build(WebActivity.AROUTER_TAG).navigation();
            }else if (item.getTitle().equals(btTxts[6])){
                ARouter.getInstance().build(KeyboardActivity.AROUTER_TAG).navigation();
            }else if (item.getTitle().equals(btTxts[7])){
                ARouter.getInstance().build(ShadowActivity.AROUTER_TAG).navigation();
            }else if (item.getTitle().equals(btTxts[8])){
                ARouter.getInstance().build(DancingNumberActivity.AROUTER_TAG).navigation();
            }else if(item.getTitle().equals(btTxts[9])){
                ARouter.getInstance().build(BreakPointResumeActivity.AROUTER_TAG).navigation();
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onStart(SHARE_MEDIA share_media) {
        LogUtil.d("SHARE onStart" + share_media.toSnsPlatform().mPlatform);
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        LogUtil.d("SHARE onResult");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        LogUtil.d("SHARE onError");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        LogUtil.d("SHARE onCancel");
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            LogUtil.d(data);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };


}
