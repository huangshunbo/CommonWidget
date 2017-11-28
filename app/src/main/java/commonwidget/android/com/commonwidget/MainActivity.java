package commonwidget.android.com.commonwidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.commonwidget.basic.activity.AbstractMvpActivity;

public class MainActivity extends AbstractMvpActivity {


    @Override
    public int initView() {
        return R.layout.activity_main;
    }
}
