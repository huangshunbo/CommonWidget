package com.android.commonwidget.fragment.widget.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.basiclib.log.LogUtil;
import com.android.basiclib.ui.inflate.ELayout;
import com.android.basiclib.ui.inflate.EWidget;
import com.android.basiclib.ui.mvp.IBasePresenter;
import com.android.basiclib.ui.view.AbstractMvpActivity;
import com.android.commonwidget.R;
import com.android.widgetlib.keyboard.EditView;
import com.android.widgetlib.keyboard.SKeyboardView;

@ELayout(Layout = R.layout.activity_keyboard)
@Route(path = KeyboardActivity.AROUTER_TAG)
public class KeyboardActivity extends AbstractMvpActivity implements EditView.OnKeyboardListener{
    public static final String AROUTER_TAG = "/commonwidget/KeyboardActivity";
    @EWidget(id = R.id.activity_keyboard_edit)
    EditView editView;
    @EWidget(id = R.id.keyboard_view)
    SKeyboardView sKeyboardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editView.setEditView(sKeyboardView,false);
        editView.setOnKeyboardListener(this);
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onHide(boolean isCompleted) {

    }

    @Override
    public void onShow() {

    }

    @Override
    public void onPress(int primaryCode) {
        LogUtil.d(primaryCode);
    }
}
