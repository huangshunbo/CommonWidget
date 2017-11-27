package commonwidget.android.com.commonwidget;

import android.app.Activity;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import commonwidget.android.com.basiclib.ui.inflate.ELayout;
import commonwidget.android.com.basiclib.ui.inflate.EWidget;
import commonwidget.android.com.basiclib.ui.mvp.IBasePresenter;
import commonwidget.android.com.basiclib.ui.view.AbstractMvpActivity;

@ELayout(Layout = R.layout.activity_main)
public class MainActivity extends AbstractMvpActivity {

    @EWidget(id = R.id.hello)
    TextView tvHello;

    @Override
    protected void onResume() {
        super.onResume();
        tvHello.setText("Hello World");
        showDataLoading();
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
}
