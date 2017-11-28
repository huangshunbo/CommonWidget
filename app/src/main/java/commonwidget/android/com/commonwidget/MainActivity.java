package commonwidget.android.com.commonwidget;

import android.app.Activity;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tencent.bugly.crashreport.CrashReport;

import commonwidget.android.com.basiclib.log.LogUtil;
import commonwidget.android.com.basiclib.ui.inflate.ELayout;
import commonwidget.android.com.basiclib.ui.inflate.EWidget;
import commonwidget.android.com.basiclib.ui.mvp.IBasePresenter;
import commonwidget.android.com.basiclib.ui.view.AbstractMvpActivity;
import commonwidget.android.com.commonwidget.contract.IMainActivityContract;
import commonwidget.android.com.commonwidget.presenter.MainPresenter;

@ELayout(Layout = R.layout.activity_main)
public class MainActivity extends AbstractMvpActivity implements IMainActivityContract.IView{

    MainPresenter mainPresenter;
    @EWidget(id = R.id.hello)
    TextView tvHello;

    @Override
    protected void onResume() {
        super.onResume();
        tvHello.setText("Hello World");
        showDataLoading();
        mainPresenter.requestData();
        CrashReport.testJavaCrash();
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
