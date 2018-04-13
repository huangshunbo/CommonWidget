package com.android.commonwidget.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.basiclib.bean.Student;
import com.android.basiclib.data.DLocal;
import com.android.basiclib.data.DStudent;
import com.android.basiclib.data.factory.DaoFactory;
import com.android.basiclib.log.LogUtil;
import com.android.basiclib.net.download.DownloadListener;
import com.android.basiclib.net.download.DownloadManager;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.commonwidget.R;
import com.android.commonwidget.bean.User;
import com.android.commonwidget.bean.Work;
import com.android.commonwidget.fragment.home.HomeFragment;
import com.android.commonwidget.fragment.me.MeFragment;
import com.android.commonwidget.fragment.widget.WidgetFragment;
import com.android.widgetlib.tabviewpager.TabViewPagerManager;
import com.networkbench.agent.impl.NBSAppAgent;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


@Route(path = MainActivity.AROUTER_TAG)
public class MainActivity extends BaseActivity implements TabViewPagerManager.OnTVPListener{
    public static final String AROUTER_TAG = "/commonwidget/MainActivity";
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    List<Fragment> tabFragmentList = new ArrayList<>();
    TabViewPagerManager tabViewPagerManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabFragment();
        init();

    }

    @Override
    protected void onCheck() {
        super.onCheck();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        }
    }

    private void initTabFragment() {
        Fragment home = getSavedInstanceFragment(HomeFragment.class);
        Fragment widget = getSavedInstanceFragment(WidgetFragment.class);
        Fragment me = getSavedInstanceFragment(MeFragment.class);
        tabFragmentList.add(home);
        tabFragmentList.add(widget);
        tabFragmentList.add(me);
        tabViewPagerManager = new TabViewPagerManager.Builder(this,getMainLayout(),tabFragmentList,this,getSupportFragmentManager())
                .defaultTabDrawables(R.drawable.icon_home,R.drawable.icon_widget,R.drawable.icon_me)
                .style(TabViewPagerManager.ORI_STYLE.BOTTOM)
                .defaultTabTxts("Home",null,"Me")
                .animation(R.anim.scale_out)
                .defaultTabBackgroundWithColor(Color.parseColor("#333333"))
                .tabBigSize(1)
                .build();
        tabViewPagerManager.updateTabNum(2,2);
//        tabViewPagerManager.updateTabNetIcon(1,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513316684230&di=1610d71752e1ca0f53ca563d4c12bba0&imgtype=0&src=http%3A%2F%2Fwww.icosky.com%2Ficon%2Fpng%2FHoliday%2FWinter%2520Wonderland%2FSnowflake.png");
    }

    private void init() {
        //性能管理 听云SDK
        NBSAppAgent.setLicenseKey("8ee9ab73668a4392b5bc5d80c4971a76").start(this);
        EventBus.getDefault().post(new User("huangshunbo",27));

//        startActivity(ArouterTestActivity.class);
//        ARouter.getInstance().build(ArouterTestActivity.AROUTER_TAG).navigation();

        Student student = new Student("huangshunbo",27);
        DStudent dStudent = DaoFactory.getInstace(DStudent.class);
        dStudent.insert(student);
        LogUtil.d("GreenDao",dStudent.loadAll());
        student.setAge(100);
        dStudent.update(student);
        LogUtil.d("GreenDao",dStudent.loadAll());
        dStudent.delete(student);
        LogUtil.d("GreenDao",dStudent.loadAll());

        DLocal.save("name","huangshunbo");
        DLocal.printAll();
        DLocal.save("name","huangshunbo");
        LogUtil.d(DLocal.getString("name",""));
        DLocal.save("names","huangshunbos");
        DLocal.printAll();

        //        极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

//        requestCodeQRCodePermissions();
    }

//    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
//    private void requestCodeQRCodePermissions() {
//        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
//        if (!EasyPermissions.hasPermissions(this, perms)) {
//            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
//        }
//    }
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean isIntrusionStatuBar() {
        return true;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceived1(User event){
        LogUtil.d("onEventReceived1" + event.getName());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceived2(Work event){
//        LogUtil.d("onEventReceived2"+event.getProjectName());
    }
}
