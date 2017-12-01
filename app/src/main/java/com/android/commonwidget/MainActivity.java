package com.android.commonwidget;

import android.widget.TextView;

import com.android.basiclib.bean.Student;
import com.android.basiclib.data.DLocalStorage;
import com.android.basiclib.data.DStudent;
import com.android.basiclib.data.DaoFactory;
import com.android.commonwidget.bean.User;
import com.android.commonwidget.bean.Work;
import com.networkbench.agent.impl.NBSAppAgent;

import com.android.basiclib.log.LogUtil;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.inflate.EWidget;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.basiclib.ui.view.AbstractMvpActivity;
import commonwidget.android.com.commonwidget.R;
import com.android.commonwidget.contract.IMainActivityContract;
import com.android.commonwidget.presenter.MainPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@ELayout(Layout = R.layout.activity_main)
public class MainActivity extends AbstractMvpActivity implements IMainActivityContract.IView{

    MainPresenter mainPresenter;
    @EWidget(id = R.id.hello)
    TextView tvHello;

    @Override
    protected void onResume() {
        super.onResume();
        tvHello.setText("Hello World");
//        showDataLoading();
        mainPresenter.requestData();
        //性能管理 听云SDK
        NBSAppAgent.setLicenseKey("8ee9ab73668a4392b5bc5d80c4971a76").start(this.getApplicationContext());
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

        DLocalStorage.add("1","huangshunbo");
        LogUtil.d(DLocalStorage.loadAll());
        DLocalStorage.add("1","hsb");
        LogUtil.d(DLocalStorage.loadAll());
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
    @Override
    protected IBasePresenter createPresenter() {
        mainPresenter = new MainPresenter();
        return mainPresenter;
    }

    @Override
    public void showData() {
        LogUtil.d("成功获取数据");
    }
}
